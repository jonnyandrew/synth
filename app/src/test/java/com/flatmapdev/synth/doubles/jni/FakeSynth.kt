package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.Synth

class FakeSynth(
    private val version: String = "1.2.3"
): Synth {
    override fun getVersion() = version
}