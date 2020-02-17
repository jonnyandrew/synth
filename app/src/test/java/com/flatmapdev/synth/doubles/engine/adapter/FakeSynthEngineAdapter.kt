package com.flatmapdev.synth.doubles.engine.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.keyboardCore.model.Key

class FakeSynthEngineAdapter(
    private val version: String = "1.0.0"
) : SynthEngineAdapter {
    override fun getVersion(): String {
        return version
    }

    override fun start() {

    }

    override fun playNote(key: Key) {

    }

    override fun stopNote() {

    }
}