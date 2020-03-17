package com.flatmapdev.synth.shared.core.model

import kotlin.math.roundToInt

typealias Fraction = Float

fun Fraction.toPercent(): Percent {
    return (this * 100).roundToInt()
}
