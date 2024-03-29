package com.flatmapdev.synth.synthUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.useCase.GetKeyboard
import com.flatmapdev.synth.keyboardCore.useCase.PlayKey
import com.flatmapdev.synth.keyboardCore.useCase.StopKeys
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SynthViewModel(
    private val getKeyboard: GetKeyboard,
    private val playKey: PlayKey,
    private val stopKeys: StopKeys
) : ViewModel() {

    private val _keyboard = MutableStateFlow<List<Key>>(emptyList())
    val keyboard: Flow<List<Key>> = _keyboard

    fun init() {
        viewModelScope.launch {
            getKeyboard.execute()
                .collect { _keyboard.value = it }
        }
    }

    fun playKey(key: Key) {
        playKey.execute(key)
    }

    fun stopKeys() {
        stopKeys.execute()
    }

    @Reusable
    class Factory @Inject constructor(
        private val getKeyboard: GetKeyboard,
        private val playKey: PlayKey,
        private val stopKeys: StopKeys
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SynthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                SynthViewModel(
                    getKeyboard,
                    playKey,
                    stopKeys
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
