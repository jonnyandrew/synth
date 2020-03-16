package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.SynthFilter

class FakeSynthFilter(
    private var cutoff: Float = 10000F,
    private var resonance: Float = 2F
) : SynthFilter {
    override fun setIsActive(isActive: Boolean) {
    }

    override fun setCutoff(cutoff: Float) {
        this.cutoff = cutoff
    }

    override fun setResonance(resonance: Float) {
        this.resonance = resonance
    }
}
