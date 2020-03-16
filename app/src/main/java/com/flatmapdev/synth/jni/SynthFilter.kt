package com.flatmapdev.synth.jni

interface SynthFilter {
    fun setIsActive(isActive: Boolean)
    fun setCutoff(cutoff: Float)
    fun setResonance(resonance: Float)
}
