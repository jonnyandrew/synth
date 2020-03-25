package com.flatmapdev.synth.jni

import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.shared.core.model.Fraction

interface SynthFilter {
    fun getFilter(synth: Pointer): FilterData
    fun setIsActive(synth: Pointer, isActive: Boolean)
    fun setCutoff(synth: Pointer, cutoff: Float)
    fun setResonance(synth: Pointer, resonance: Fraction)
}
