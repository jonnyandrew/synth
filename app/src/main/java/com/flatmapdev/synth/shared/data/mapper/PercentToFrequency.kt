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
    val value = minFrequency + fractionalFrequency * frequencyRange
    return Frequency(value)
}

fun Frequency.toPercent(): Percent {
    val fractionalFrequency = (this.value - minFrequency) / frequencyRange
    return fractionalFrequency.pow(1 / curveExponent).toPercent()
}
