package com.flatmapdev.synth.doubles.filter.model

import com.flatmapdev.synth.filterData.model.FilterData

fun createFilterData(
    cutoffFrequency: Float = 10000f,
    resonance: Float = 0.5f
) = FilterData(
    cutoffFrequency,
    resonance
)
