package com.flatmapdev.synth.keyboardData.adapter

import android.content.SharedPreferences
import androidx.core.content.edit
import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardData.mapper.parseNoteFromSharedPreferencesString
import com.flatmapdev.synth.keyboardData.mapper.parseScaleTypeFromSharedPreferencesString
import com.flatmapdev.synth.keyboardData.mapper.toSharedPreferencesString
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Named

class SharedPreferencesScaleAdapter @Inject constructor(
    @Named(SHARED_PREFERENCES_NAME)
    val sharedPreferences: SharedPreferences
) : ScaleAdapter {
//    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
//    @ExperimentalCoroutinesApi
    private val keyChanged = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key -> offer(key) }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

//    @ExperimentalCoroutinesApi
    override fun getScale(): Flow<Scale?> {
//        @UseExperimental(ExperimentalCoroutinesApi::class)
        return keyChanged
            // Emit something to start with even if no key is changed
            .map { Unit }
            .onStart { emit(Unit) }
            .map {
                val scaleType =
                    sharedPreferences.getString(SHARED_PREFERENCES_KEY_SCALE_TYPE, null)?.let(
                        ::parseScaleTypeFromSharedPreferencesString
                    ) ?: return@map null

                val tonic = sharedPreferences.getString(SHARED_PREFERENCES_KEY_TONIC, null)?.let(
                    ::parseNoteFromSharedPreferencesString
                ) ?: return@map null

                Scale(tonic, scaleType)
            }
            .distinctUntilChanged()
    }

    override fun storeScale(scale: Scale) {
        sharedPreferences.edit {
            putString(SHARED_PREFERENCES_KEY_TONIC, scale.tonic.toSharedPreferencesString())
            putString(SHARED_PREFERENCES_KEY_SCALE_TYPE, scale.type.toSharedPreferencesString())
        }
    }

    companion object {
        const val SHARED_PREFERENCES_NAME: String = "SharedPreferencesScaleAdapter"
        const val SHARED_PREFERENCES_KEY_TONIC = "Note"
        const val SHARED_PREFERENCES_KEY_SCALE_TYPE = "ScaleType"
    }
}