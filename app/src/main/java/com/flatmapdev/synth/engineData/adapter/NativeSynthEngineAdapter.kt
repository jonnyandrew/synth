package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.engineData.mapper.toEnvelope
import com.flatmapdev.synth.engineData.mapper.toFloatArray
import com.flatmapdev.synth.engineData.mapper.toPitch
import com.flatmapdev.synth.jni.NativeSynth
import com.flatmapdev.synth.keyboardCore.model.Key
import javax.inject.Inject

class NativeSynthEngineAdapter @Inject constructor(
    private val nativeSynth: NativeSynth
) : SynthEngineAdapter {
    override fun getVersion() = nativeSynth.getVersion()

    override fun start() = nativeSynth.start()

    override fun stop() = nativeSynth.stop()

    override fun playNote(key: Key) {
        nativeSynth.playNote(
            key.toPitch()
        )
    }

    override fun stopNote() = nativeSynth.stopNote()

    override fun getAmpEnvelope(): Envelope {
        return nativeSynth.getAmpEnvelope()
            .toEnvelope()
    }

    override fun setAmpEnvelope(envelope: Envelope) {
        nativeSynth.setAmpEnvelope(
            envelope.toFloatArray()
        )
    }
}