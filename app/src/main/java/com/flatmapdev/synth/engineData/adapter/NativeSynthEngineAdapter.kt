package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.jni.Synth
import javax.inject.Inject

class NativeSynthEngineAdapter @Inject constructor(
    private val synth: Synth
) : SynthEngineAdapter {
    override fun getVersion(): String {
        return synth.getVersion()
    }
}