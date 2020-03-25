package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.engineData.mapper.toEnvelope
import com.flatmapdev.synth.engineData.mapper.toFloatArray
import com.flatmapdev.synth.engineData.mapper.toPitch
import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthEngine
import com.flatmapdev.synth.keyboardCore.model.Key
import javax.inject.Inject

class DefaultSynthEngineAdapter @Inject constructor(
    private val synth: Pointer,
    private val synthEngine: SynthEngine
) : SynthEngineAdapter {
    override val version get() = synthEngine.getVersion()

    override fun start() = synthEngine.start(synth)

    override fun stop() = synthEngine.stop(synth)

    override fun cleanUp() = synthEngine.cleanUp(synth)

    override fun playNote(key: Key) {
        synthEngine.playNote(
            synth,
            key.toPitch()
        )
    }

    override fun stopNote() = synthEngine.stopNote(synth)

    override var ampEnvelope: Envelope
        get() {
            return synthEngine.getAmpEnvelope(synth)
                .toEnvelope()
        }
        set(envelope) {
            synthEngine.setAmpEnvelope(
                synth,
                envelope.toFloatArray()
            )
        }
}
