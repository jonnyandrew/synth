package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.model.Oscillator

class FakeSynthOscillator(
    private var oscillator: Oscillator = createOscillator()
) : SynthOscillator {
    override fun getOscillator(): Oscillator {
        return oscillator
    }

    override fun setOscillator(oscillator: Oscillator) {
        this.oscillator = oscillator
    }
}
