package com.flatmapdev.synth.keyboardData

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import com.flatmapdev.synth.keyboardData.adapter.SharedPreferencesScaleAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class KeyboardDataModule {
    @Binds
    abstract fun scaleAdapter(impl: SharedPreferencesScaleAdapter): ScaleAdapter

    companion object {
        @Named(SharedPreferencesScaleAdapter.SHARED_PREFERENCES_NAME)
        @Provides
        @JvmStatic
        fun scaleAdapterSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(
                SharedPreferencesScaleAdapter.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
            )
        }
    }
}

