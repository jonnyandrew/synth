package com.flatmapdev.synth.engineData.mapper

import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note

private const val MIDDLE_C_PITCH = 60
private const val MIDDLE_C_OCTAVE = 4
private const val MAX_PITCH = 127
private const val MIN_PITCH = 0

/**
 * Standard MIDI pitches are in the range 0 to 127 with middle C at pitch 60
 * Notes below or above this range should map to the min or max pitches respectively
 */
fun Key.toPitch(): Int {
    val octaveSize = Note.values().size
    val octaveStart = MIDDLE_C_PITCH + (this.octave - MIDDLE_C_OCTAVE) * octaveSize
    val positionInOctave = note.ordinal
    val pitch = octaveStart + positionInOctave
    return pitch.coerceIn(MIN_PITCH, MAX_PITCH)
}
