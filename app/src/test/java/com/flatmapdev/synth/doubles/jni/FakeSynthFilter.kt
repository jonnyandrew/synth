package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.doubles.filter.model.createFilterData
import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthFilter

class FakeSynthFilter(
    private var filterData: FilterData = createFilterData()
) : SynthFilter {
    override fun getFilter(synth: Pointer): FilterData {
        return filterData
    }

    override fun setIsActive(synth: Pointer, isActive: Boolean) {
    }

    override fun setCutoff(synth: Pointer, cutoff: Float) {
        filterData = filterData.copy(cutoffFrequency = cutoff)
    }

    override fun setResonance(synth: Pointer, resonance: Float) {
        filterData = filterData.copy(resonance = resonance)
    }
}
