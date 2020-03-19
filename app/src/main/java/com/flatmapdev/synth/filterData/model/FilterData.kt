package com.flatmapdev.synth.filterData.model

import androidx.annotation.Keep

@Keep
data class FilterData(
    val cutoffFrequency: Float,
    val resonance: Float
)
