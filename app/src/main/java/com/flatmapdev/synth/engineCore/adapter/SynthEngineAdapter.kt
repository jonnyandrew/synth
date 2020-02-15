package com.flatmapdev.synth.engineCore.adapter

import com.flatmapdev.synth.keyboardCore.model.Key

interface SynthEngineAdapter {
    fun getVersion(): String
    fun start()
    fun playNote(key: Key)
    fun stopNote()
}