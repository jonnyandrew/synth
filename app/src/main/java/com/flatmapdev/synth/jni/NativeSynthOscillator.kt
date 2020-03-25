package com.flatmapdev.synth.jni

import androidx.annotation.Keep
import com.flatmapdev.synth.oscillatorData.model.OscillatorData
import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
@Keep
class NativeSynthOscillator @Inject constructor(
    @Suppress("unused")
    private val oscillatorId: Int
) : SynthOscillator {
    external override fun getOscillator(synth: Pointer): OscillatorData
    external override fun setOscillator(synth: Pointer, oscillator: OscillatorData)
    external override fun setWaveform(synth: Pointer, waveform: Int)
    external override fun setPitchOffset(synth: Pointer, pitchOffset: Int)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
