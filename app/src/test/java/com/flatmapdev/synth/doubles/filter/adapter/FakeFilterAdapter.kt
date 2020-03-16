package com.flatmapdev.synth.doubles.filter.adapter

import com.flatmapdev.synth.doubles.filter.model.createFilter
import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.shared.core.model.Percent

class FakeFilterAdapter(
    private var filter: Filter = createFilter()
) : FilterAdapter {
    override fun getFilter(): Filter = filter

    override fun setIsActive(isActive: Boolean) {}

    override fun setCutoff(cutoffFrequency: Float) {
        filter = filter.copy(cutoffFrequency = cutoffFrequency)
    }

    override fun setResonance(resonance: Percent) {
        filter = filter.copy(resonance = resonance)
    }
}
