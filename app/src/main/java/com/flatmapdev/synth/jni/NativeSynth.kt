package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynth @Inject constructor() : Synth {
    external override fun getVersion(): String

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}