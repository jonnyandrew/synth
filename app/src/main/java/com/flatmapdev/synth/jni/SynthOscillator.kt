package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorData.model.OscillatorData

interface SynthOscillator {
    fun getOscillator(): OscillatorData
    fun setOscillator(oscillator: OscillatorData)
    fun setWaveform(waveform: Int)
}
