package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.jni.NativeSynth
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

    override fun playNote() {
        nativeSynth.playNote()
    }

    override fun stopNote() {
        nativeSynth.stopNote()
    }
}