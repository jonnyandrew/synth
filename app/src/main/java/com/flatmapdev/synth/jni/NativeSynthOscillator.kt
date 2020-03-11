package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorData.model.OscillatorData
import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynthOscillator @Inject constructor(
    @Suppress("unused")
    private val oscillatorId: Int
) : SynthOscillator {
    external override fun getOscillator(): OscillatorData
    external override fun setOscillator(oscillator: OscillatorData)
    external override fun setWaveform(waveform: Int)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
