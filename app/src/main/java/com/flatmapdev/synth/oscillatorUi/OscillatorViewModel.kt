package com.flatmapdev.synth.oscillatorUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.oscillatorCore.useCase.GetOscillator
import com.flatmapdev.synth.oscillatorCore.useCase.SetOscillator
import dagger.Reusable
import javax.inject.Inject

class OscillatorViewModel(
    private val getOscillator: GetOscillator,
    private val setOscillator: SetOscillator
) : ViewModel() {
    private val _oscillator = MutableLiveData<Oscillator>()
    val oscillator: LiveData<Oscillator> = _oscillator

    fun init(oscillatorId: OscillatorId) {
        _oscillator.value = getOscillator.execute(oscillatorId)
    }

    fun setOscillator(oscillatorId: OscillatorId, oscillator: Oscillator) {
        setOscillator.execute(oscillatorId, oscillator)
    }

    @Reusable
    class Factory @Inject constructor(
        private val getOscillator: GetOscillator,
        private val setOscillator: SetOscillator
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(OscillatorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                OscillatorViewModel(
                    getOscillator,
                    setOscillator
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
