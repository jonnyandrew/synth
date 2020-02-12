package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineData.mapper.toPitch
import com.flatmapdev.synth.jni.NativeSynth
import com.flatmapdev.synth.keyboardCore.model.Key
import javax.inject.Inject

class NativeSynthEngineAdapter @Inject constructor(
    private val nativeSynth: NativeSynth
) : SynthEngineAdapter {
    override fun getVersion(): String {
        return nativeSynth.getVersion()
    }

    override fun start() {
        nativeSynth.start()
    }

    override fun playNote(key: Key) {
        nativeSynth.playNote(
            key.toPitch()
        )
    }

    override fun stopNote(key: Key) {
        nativeSynth.stopNote()
    }
}