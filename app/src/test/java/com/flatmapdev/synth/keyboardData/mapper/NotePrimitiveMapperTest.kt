package com.flatmapdev.synth.keyboardData.mapper

import org.junit.Test

class NotePrimitiveMapperTest() {
    @Test(expected = IllegalArgumentException::class)
    fun `when trying to parse unknown note, it throws`() {
        parseNoteFromSharedPreferencesString("not a note")
    }
}