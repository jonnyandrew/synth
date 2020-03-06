package com.flatmapdev.synth.keyboardData.mapper

import com.flatmapdev.synth.keyboardCore.model.Note

/**
 * Constants for notes which don't depend on the domain model.
 * Better than using Enum.toString() or Enum.ordinal() that will
 * change if the domain model code changes.
 */
private const val NOTE_C = "C"
private const val NOTE_C_SHARP_D_FLAT = "C_SHARP_D_FLAT"
private const val NOTE_D = "D"
private const val NOTE_D_SHARP_E_FLAT = "D_SHARP_E_FLAT"
private const val NOTE_E = "E"
private const val NOTE_F = "F"
private const val NOTE_F_SHARP_G_FLAT = "F_SHARP_G_FLAT"
private const val NOTE_G = "G"
private const val NOTE_G_SHARP_A_FLAT = "G_SHARP_A_FLAT"
private const val NOTE_A = "A"
private const val NOTE_A_SHARP_B_FLAT = "A_SHARP_B_FLAT"
private const val NOTE_B = "B"

internal fun Note.toSharedPreferencesString(): String = when (this) {
    Note.C -> NOTE_C
    Note.C_SHARP_D_FLAT -> NOTE_C_SHARP_D_FLAT
    Note.D -> NOTE_D
    Note.D_SHARP_E_FLAT -> NOTE_D_SHARP_E_FLAT
    Note.E -> NOTE_E
    Note.F -> NOTE_F
    Note.F_SHARP_G_FLAT -> NOTE_F_SHARP_G_FLAT
    Note.G -> NOTE_G
    Note.G_SHARP_A_FLAT -> NOTE_G_SHARP_A_FLAT
    Note.A -> NOTE_A
    Note.A_SHARP_B_FLAT -> NOTE_A_SHARP_B_FLAT
    Note.B -> NOTE_B
}

internal fun parseNoteFromSharedPreferencesString(value: String): Note = when (value) {
    NOTE_C -> Note.C
    NOTE_C_SHARP_D_FLAT -> Note.C_SHARP_D_FLAT
    NOTE_D -> Note.D
    NOTE_D_SHARP_E_FLAT -> Note.D_SHARP_E_FLAT
    NOTE_E -> Note.E
    NOTE_F -> Note.F
    NOTE_F_SHARP_G_FLAT -> Note.F_SHARP_G_FLAT
    NOTE_G -> Note.G
    NOTE_G_SHARP_A_FLAT -> Note.G_SHARP_A_FLAT
    NOTE_A -> Note.A
    NOTE_A_SHARP_B_FLAT -> Note.A_SHARP_B_FLAT
    NOTE_B -> Note.B
    else -> throw IllegalArgumentException("Unknown note: $value")
}
