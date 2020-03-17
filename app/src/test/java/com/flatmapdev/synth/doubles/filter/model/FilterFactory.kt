package com.flatmapdev.synth.doubles.filter.model

import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.shared.core.model.Frequency
import com.flatmapdev.synth.shared.core.model.Percent

fun createFilter(
    cutoff: Frequency = Frequency(10000f),
    resonance: Percent = 50
) = Filter(
    cutoff,
    resonance
)
