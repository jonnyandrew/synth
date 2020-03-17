package com.flatmapdev.synth.filterCore.model

import com.flatmapdev.synth.shared.core.model.Frequency
import com.flatmapdev.synth.shared.core.model.Percent

data class Filter(
    val cutoff: Frequency,
    val resonance: Percent
)
