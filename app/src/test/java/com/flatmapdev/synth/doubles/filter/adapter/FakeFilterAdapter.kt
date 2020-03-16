package com.flatmapdev.synth.doubles.filter.adapter

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.shared.core.model.Percent

class FakeFilterAdapter : FilterAdapter {
    override fun setIsActive(isActive: Boolean) {}

    override fun setCutoff(cutoffFrequency: Float) {}

    override fun setResonance(resonance: Percent) {}
}
