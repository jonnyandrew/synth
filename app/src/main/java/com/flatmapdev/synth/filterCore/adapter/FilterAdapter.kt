package com.flatmapdev.synth.filterCore.adapter

import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.shared.core.model.Percent

interface FilterAdapter {
    fun getFilter(): Filter
    fun setIsActive(isActive: Boolean)
    fun setCutoff(cutoffFrequency: Float)
    fun setResonance(resonance: Percent)
}
