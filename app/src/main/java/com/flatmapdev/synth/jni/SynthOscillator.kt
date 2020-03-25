package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorData.model.OscillatorData

interface SynthOscillator {
    fun getOscillator(synth: Pointer): OscillatorData
    fun setOscillator(synth: Pointer, oscillator: OscillatorData)
    fun setWaveform(synth: Pointer, waveform: Int)
    fun setPitchOffset(synth: Pointer, pitchOffset: Int)
}
