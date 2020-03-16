package com.flatmapdev.synth.doubles.filter.model

import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.shared.core.model.Percent

fun createFilter(
    cutoffFrequency: Float = 10000f,
    resonance: Percent = 50
) = Filter(
    cutoffFrequency,
    resonance
)
