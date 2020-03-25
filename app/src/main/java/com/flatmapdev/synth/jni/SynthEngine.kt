package com.flatmapdev.synth.jni

interface SynthEngine {
    fun initialize(): Pointer
    fun cleanUp(synth: Pointer)
    fun getVersion(): String
    fun start(synth: Pointer)
    fun stop(synth: Pointer)
    fun playNote(synth: Pointer, pitch: Int)
    fun stopNote(synth: Pointer)
    fun getAmpEnvelope(synth: Pointer): FloatArray
    fun setAmpEnvelope(synth: Pointer, envelopeAdsr: FloatArray)
}
