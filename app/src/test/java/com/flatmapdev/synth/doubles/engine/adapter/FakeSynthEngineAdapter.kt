package com.flatmapdev.synth.doubles.engine.adapter

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import javax.inject.Inject

class FakeSynthEngineAdapter @Inject constructor(): SynthEngineAdapter {
    override fun getVersion(): String {
        return "1.0.0"
    }
}