package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthEngine

class FakeSynthEngine(
    private val version: String = "1.0.0",
    private var ampEnvelope: FloatArray = floatArrayOf(0f, 2f, 10f, 4f)
) : SynthEngine {
    override fun initialize(): Pointer { return 0 }

    override fun cleanUp(synth: Pointer) {}

    override fun getVersion(): String = version
    override fun start(synth: Pointer) {}

    override fun stop(synth: Pointer) {}

    override fun playNote(synth: Pointer, pitch: Int) {}

    override fun stopNote(synth: Pointer) {}

    override fun getAmpEnvelope(synth: Pointer) = ampEnvelope

    override fun setAmpEnvelope(synth: Pointer, envelopeAdsr: FloatArray) {
        ampEnvelope = envelopeAdsr
    }
}
