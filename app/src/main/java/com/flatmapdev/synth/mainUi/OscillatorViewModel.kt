package com.flatmapdev.synth.mainUi

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

    private val _oscillator1 = MutableLiveData<Oscillator>()
    val oscillator1: LiveData<Oscillator> = _oscillator1
    private val _oscillator2 = MutableLiveData<Oscillator>()
    val oscillator2: LiveData<Oscillator> = _oscillator2

    fun setOscillator1(oscillator: Oscillator) {
        setOscillator.execute(OscillatorId.Osc1, oscillator)
    }

    fun setOscillator2(oscillator: Oscillator) {
        setOscillator.execute(OscillatorId.Osc2, oscillator)
    }

    fun init() {
        _oscillator1.value = getOscillator.execute(OscillatorId.Osc1)
        _oscillator2.value = getOscillator.execute(OscillatorId.Osc2)
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
