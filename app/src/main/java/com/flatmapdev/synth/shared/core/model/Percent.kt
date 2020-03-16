package com.flatmapdev.synth.shared.core.model

typealias Percent = Int

fun Percent.toFraction(): Float {
    return this.toFloat() / 100
}
