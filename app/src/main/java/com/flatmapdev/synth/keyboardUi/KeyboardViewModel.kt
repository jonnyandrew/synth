package com.flatmapdev.synth.keyboardUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import com.flatmapdev.synth.keyboardCore.useCase.GetScale
import com.flatmapdev.synth.keyboardCore.useCase.SetScale
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KeyboardViewModel(
    private val getScale: GetScale,
    private val setScale: SetScale
) : ViewModel() {
    private val _scale = MutableLiveData<Scale>()
    val scale: LiveData<Scale> = _scale

    fun init() {
        viewModelScope.launch {
            getScale.execute().collect { scale ->
                _scale.value = scale
            }
        }
    }

    fun setScaleTonic(tonic: Note) {
        val scale = _scale.value ?: throw IllegalStateException("Scale not available yet")

        setScale.execute(scale.copy(tonic = tonic))
    }

    fun setScaleType(scaleType: ScaleType) {
        val scale = _scale.value ?: throw IllegalStateException("Scale not available yet")

        setScale.execute(scale.copy(type = scaleType))
    }

    @Reusable
    class Factory @Inject constructor(
        private val getScale: GetScale,
        private val setScale: SetScale
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(KeyboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                KeyboardViewModel(
                    getScale,
                    setScale
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
