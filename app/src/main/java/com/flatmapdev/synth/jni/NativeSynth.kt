package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynth @Inject constructor() : Synth {
    external override fun getVersion(): String
    external override fun start()
    external override fun playNote()
    external override fun stopNote()

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}