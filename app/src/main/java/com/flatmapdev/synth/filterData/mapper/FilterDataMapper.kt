package com.flatmapdev.synth.filterData.mapper

import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.shared.core.model.toPercent

fun FilterData.toDomainModel(): Filter {
    return Filter(
        cutoffFrequency,
        resonance.toPercent()
    )
}
