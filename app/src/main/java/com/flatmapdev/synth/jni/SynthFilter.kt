package com.flatmapdev.synth.jni

import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.shared.core.model.Fraction

interface SynthFilter {
    fun getFilter(): FilterData
    fun setIsActive(isActive: Boolean)
    fun setCutoff(cutoff: Float)
    fun setResonance(resonance: Fraction)
}
