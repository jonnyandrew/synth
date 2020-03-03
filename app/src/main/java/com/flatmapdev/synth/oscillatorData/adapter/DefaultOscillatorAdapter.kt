package com.flatmapdev.synth.oscillatorData.adapter

import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator

class DefaultOscillatorAdapter(
    private val synthOscillator: SynthOscillator
) : OscillatorAdapter {
    override var oscillator: Oscillator
        get() = synthOscillator.getOscillator()
        set(value) = synthOscillator.setOscillator(value)
}
