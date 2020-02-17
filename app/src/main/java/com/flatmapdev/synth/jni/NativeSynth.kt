package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynth @Inject constructor() {
    external fun getVersion(): String
    external fun start()
    external fun stop()
    external fun playNote(pitch: Int)
    external fun stopNote()

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}