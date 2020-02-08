package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class Synth @Inject constructor() {
    external fun getVersion(): String

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}