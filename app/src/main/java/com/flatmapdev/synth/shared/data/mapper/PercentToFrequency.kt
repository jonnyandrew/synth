package com.flatmapdev.synth.shared.data.mapper

import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.core.model.toFraction
import kotlin.math.pow

fun Percent.toFrequency(): Float {
    val minFrequency = 100
    val maxFrequency = 20000
    val exponent = 4.0F
    val frequencyRange = maxFrequency - minFrequency
    val fractionalFrequency = this.toFraction().pow(exponent)
    return minFrequency + fractionalFrequency * frequencyRange
}
