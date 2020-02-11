package com.flatmapdev.synth.jni

interface Synth {
    fun getVersion(): String
    fun start()
    fun playNote()
    fun stopNote()
}