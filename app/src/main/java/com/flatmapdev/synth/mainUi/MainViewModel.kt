package com.flatmapdev.synth.mainUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.useCase.GetKeyboard
import com.flatmapdev.synth.keyboardCore.useCase.PlayKey
import com.flatmapdev.synth.keyboardCore.useCase.StopKeys
import dagger.Reusable
import javax.inject.Inject

class MainViewModel(
    private val getKeyboard: GetKeyboard,
    private val playKey: PlayKey,
    private val stopKeys: StopKeys
) : ViewModel() {

    private val _keyboard = MutableLiveData<List<Key>>()
    val keyboard: LiveData<List<Key>> = _keyboard

    fun init() {
        _keyboard.value = getKeyboard.execute()
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
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                MainViewModel(
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
