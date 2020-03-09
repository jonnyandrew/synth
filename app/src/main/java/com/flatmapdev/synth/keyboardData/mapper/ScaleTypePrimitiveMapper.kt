package com.flatmapdev.synth.keyboardData.mapper

import com.flatmapdev.synth.keyboardCore.model.ScaleType

/**
 * Constants for notes which don't depend on the domain model.
 * Better than using Enum.toString() or Enum.ordinal() that will
 * change if the domain model code changes.
 */
private const val MAJOR = "MAJOR"
private const val HARMONIC_MINOR = "HARMONIC_MINOR"
private const val MINOR_PENTATONIC = "MINOR_PENTATONIC"

internal fun ScaleType.toSharedPreferencesString(): String = when (this) {
    ScaleType.Major -> MAJOR
    ScaleType.HarmonicMinor -> HARMONIC_MINOR
    ScaleType.MinorPentatonic -> MINOR_PENTATONIC
}

internal fun parseScaleTypeFromSharedPreferencesString(value: String): ScaleType = when (value) {
    MAJOR -> ScaleType.Major
    HARMONIC_MINOR -> ScaleType.HarmonicMinor
    MINOR_PENTATONIC -> ScaleType.MinorPentatonic
    else -> throw IllegalArgumentException("Unknown scale type: $value")
}
