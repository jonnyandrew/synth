package com.flatmapdev.synth.filterData.adapter

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.filterData.mapper.toDomainModel
import com.flatmapdev.synth.jni.SynthFilter
import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.core.model.toFraction
import javax.inject.Inject

class DefaultFilterAdapter @Inject constructor(
    private val synthFilter: SynthFilter
) : FilterAdapter {

    override fun getFilter(): Filter {
        return synthFilter.getFilter()
            .toDomainModel()
    }

    override fun setIsActive(isActive: Boolean) {
        synthFilter.setIsActive(isActive)
    }

    override fun setCutoff(cutoffFrequency: Float) {
        synthFilter.setCutoff(cutoffFrequency)
    }

    override fun setResonance(resonance: Percent) {
        val synthFilterResonance = resonance.toFraction()

        synthFilter.setResonance(synthFilterResonance)
    }
}
