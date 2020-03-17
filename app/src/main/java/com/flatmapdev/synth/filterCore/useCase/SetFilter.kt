package com.flatmapdev.synth.filterCore.useCase

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.shared.core.model.Frequency
import com.flatmapdev.synth.shared.core.model.Percent
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SetFilter @Inject constructor(
    private val filterAdapter: FilterAdapter
) {
    fun setCutoff(cutoff: Frequency) {
        filterAdapter.setCutoff(cutoff)
    }

    fun setResonance(resonance: Percent) {
        filterAdapter.setResonance(resonance)
    }
}
