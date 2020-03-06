package com.flatmapdev.synth.keyboardData.mapper

import com.flatmapdev.synth.keyboardCore.model.ScaleType

/**
 * Constants for notes which don't depend on the domain model.
 * Better than using Enum.toString() or Enum.ordinal() that will
 * change if the domain model code changes.
 */
private const val MAJOR = "MAJOR"
private const val MINOR = "MINOR"
private const val PENTATONIC = "PENTATONIC"

internal fun ScaleType.toSharedPreferencesString(): String = when (this) {
    ScaleType.Major -> MAJOR
    ScaleType.Minor -> MINOR
    ScaleType.Pentatonic -> PENTATONIC
}

internal fun parseScaleTypeFromSharedPreferencesString(value: String): ScaleType = when (value) {
    MAJOR -> ScaleType.Major
    MINOR -> ScaleType.Minor
    PENTATONIC -> ScaleType.Pentatonic
    else -> throw IllegalArgumentException("Unknown scale type: $value")
}
