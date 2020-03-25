package com.flatmapdev.synth.filterData.adapter

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.filterData.mapper.toDomainModel
import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthFilter
import com.flatmapdev.synth.shared.core.model.Frequency
import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.core.model.toFraction
import javax.inject.Inject

class DefaultFilterAdapter @Inject constructor(
    private val synth: Pointer,
    private val synthFilter: SynthFilter
) : FilterAdapter {

    override fun getFilter(): Filter {
        return synthFilter.getFilter(synth)
            .toDomainModel()
    }

    override fun setIsActive(isActive: Boolean) {
        synthFilter.setIsActive(synth, isActive)
    }

    override fun setCutoff(cutoff: Frequency) {
        synthFilter.setCutoff(synth, cutoff.value)
    }

    override fun setResonance(resonance: Percent) {
        val synthFilterResonance = resonance.toFraction()

        synthFilter.setResonance(synth, synthFilterResonance)
    }
}
