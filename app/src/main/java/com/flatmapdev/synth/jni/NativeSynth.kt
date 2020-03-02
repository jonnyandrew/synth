package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynth @Inject constructor() : Synth {
    init {
        initialize()
    }

    external override fun initialize()
    external override fun getVersion(): String
    external override fun start()
    external override fun stop()
    external override fun playNote(pitch: Int)
    external override fun stopNote()
    external override fun getAmpEnvelope(): FloatArray
    external override fun setAmpEnvelope(envelopeAdsr: FloatArray)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
