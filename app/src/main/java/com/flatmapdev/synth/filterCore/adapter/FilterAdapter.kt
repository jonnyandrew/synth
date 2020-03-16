package com.flatmapdev.synth.filterCore.adapter

import com.flatmapdev.synth.shared.core.model.Percent

interface FilterAdapter {
    fun setIsActive(isActive: Boolean)
    fun setCutoff(cutoffFrequency: Float)
    fun setResonance(resonance: Percent)
}
