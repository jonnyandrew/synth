package com.flatmapdev.synth.shared.data.mapper

import com.flatmapdev.synth.shared.core.model.Frequency
import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.core.model.toFraction
import com.flatmapdev.synth.shared.core.model.toPercent
import kotlin.math.pow

private const val minFrequency = 100
private const val maxFrequency = 20000
private const val frequencyRange = maxFrequency - minFrequency
private const val curveExponent = 4.0F

fun Percent.toFrequency(): Frequency {
    val fractionalFrequency = this.toFraction().pow(curveExponent)
    return minFrequency + fractionalFrequency * frequencyRange
}

fun Frequency.fromFrequencyToPercent(): Percent {
    val fractionalFrequency = (this - minFrequency) / frequencyRange
    return fractionalFrequency.pow(1 / curveExponent).toPercent()
}
