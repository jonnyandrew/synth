package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class FirstNativeSynthOscillator @Inject constructor() : SynthOscillator {
    external override fun getOscillator(): Oscillator
    external override fun setOscillator(oscillator: Oscillator)
    external override fun setWaveform(type: Int)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
