package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.engineData.mapper.toEnvelope
import com.flatmapdev.synth.engineData.mapper.toFloatArray
import com.flatmapdev.synth.engineData.mapper.toPitch
import com.flatmapdev.synth.jni.Synth
import com.flatmapdev.synth.keyboardCore.model.Key
import javax.inject.Inject

class DefaultSynthEngineAdapter @Inject constructor(
    private val synth: Synth
) : SynthEngineAdapter {
    override val version get() = synth.getVersion()

    override fun start() = synth.start()

    override fun stop() = synth.stop()

    override fun playNote(key: Key) {
        synth.playNote(
            key.toPitch()
        )
    }

    override fun stopNote() = synth.stopNote()

    override var ampEnvelope: Envelope
        get() {
            return synth.getAmpEnvelope()
                .toEnvelope()
        }
        set(envelope) {
            synth.setAmpEnvelope(
                envelope.toFloatArray()
            )
        }
}