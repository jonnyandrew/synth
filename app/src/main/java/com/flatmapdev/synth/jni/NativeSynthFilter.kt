package com.flatmapdev.synth.jni

import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
class NativeSynthFilter @Inject constructor() : SynthFilter {
    external override fun setCutoff(cutoff: Float)
    external override fun setResonance(resonance: Float)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
