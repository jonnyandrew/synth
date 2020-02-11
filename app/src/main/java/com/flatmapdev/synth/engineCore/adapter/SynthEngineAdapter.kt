package com.flatmapdev.synth.engineCore.adapter

interface SynthEngineAdapter {
    fun getVersion(): String
    fun start()
    fun playNote()
    fun stopNote()
}