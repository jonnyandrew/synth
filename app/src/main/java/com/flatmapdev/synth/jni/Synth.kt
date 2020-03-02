package com.flatmapdev.synth.jni

interface Synth {
    fun initialize()
    fun getVersion(): String
    fun start()
    fun stop()
    fun playNote(pitch: Int)
    fun stopNote()
    fun getAmpEnvelope(): FloatArray
    fun setAmpEnvelope(envelopeAdsr: FloatArray)
}
