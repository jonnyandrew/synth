package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynthEngine @Inject constructor() : SynthEngine {
    external override fun initialize(): Pointer
    external override fun cleanUp(synth: Pointer)
    external override fun getVersion(): String
    external override fun start(synth: Pointer)
    external override fun stop(synth: Pointer)
    external override fun playNote(synth: Pointer, pitch: Int)
    external override fun stopNote(synth: Pointer)
    external override fun getAmpEnvelope(synth: Pointer): FloatArray
    external override fun setAmpEnvelope(synth: Pointer, envelopeAdsr: FloatArray)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
