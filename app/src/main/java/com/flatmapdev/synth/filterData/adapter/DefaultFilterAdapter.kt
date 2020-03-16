package com.flatmapdev.synth.filterData.adapter

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.jni.SynthFilter
import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.core.model.toFraction
import javax.inject.Inject

class DefaultFilterAdapter @Inject constructor(
    private val synthFilter: SynthFilter
) : FilterAdapter {
    override fun setIsActive(isActive: Boolean) {
        synthFilter.setIsActive(isActive)
    }

    override fun setCutoff(cutoffFrequency: Float) {
        synthFilter.setCutoff(cutoffFrequency)
    }

    override fun setResonance(resonance: Percent) {
        // The range of inputs for the synth filter is [0, 4]
        val synthFilterResonance = resonance.toFraction() * 4

        synthFilter.setResonance(synthFilterResonance)
    }
}
