package com.flatmapdev.synth.doubles.engine.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.keyboardCore.model.Key

class StubSynthEngineAdapter(
    override val version: String = "123",
    override var ampEnvelope: Envelope = Envelope(1, 2, 3, 4)
) : SynthEngineAdapter {

    override fun start() {}

    override fun stop() {}

    override fun playNote(key: Key) {}

    override fun stopNote() {}
}
