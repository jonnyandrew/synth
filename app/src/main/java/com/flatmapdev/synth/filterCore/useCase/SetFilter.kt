package com.flatmapdev.synth.filterCore.useCase

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.shared.core.model.Percent
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SetFilter @Inject constructor(
    private val filterAdapter: FilterAdapter
) {
    fun setCutoff(cutoffFrequency: Float) {
        filterAdapter.setIsActive(cutoffFrequency < 20000)
        filterAdapter.setCutoff(cutoffFrequency)
    }

    fun setResonance(resonance: Percent) {
        filterAdapter.setResonance(resonance)
    }
}
