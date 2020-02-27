package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.Synth

class FakeSynth(
    private val version: String = "1.0.0",
    private var ampEnvelope: FloatArray = floatArrayOf(0f, 2f, 10f, 4f)
) : Synth {
    override fun getVersion(): String = version

    override fun start() {}

    override fun stop() {}

    override fun playNote(pitch: Int) {}

    override fun stopNote() {}

    override fun getAmpEnvelope() = ampEnvelope

    override fun setAmpEnvelope(envelopeAdsr: FloatArray) {
        ampEnvelope = envelopeAdsr
    }
}
