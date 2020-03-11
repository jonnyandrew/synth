package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorCore.model.Oscillator

interface SynthOscillator {
    fun getOscillator(): Oscillator
    fun setOscillator(oscillator: Oscillator)
    fun setWaveform(type: Int)
}
