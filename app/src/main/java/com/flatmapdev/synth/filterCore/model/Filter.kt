package com.flatmapdev.synth.filterCore.model

import com.flatmapdev.synth.shared.core.model.Percent

data class Filter(
    val cutoffFrequency: Float,
    val resonance: Percent
)
