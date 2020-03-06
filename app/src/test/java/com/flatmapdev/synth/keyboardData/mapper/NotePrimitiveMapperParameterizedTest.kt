package com.flatmapdev.synth.keyboardData.mapper

import com.flatmapdev.synth.keyboardCore.model.Note
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NotePrimitiveMapperParameterizedTest(
    private val note: Note,
    private val noteString: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Array<Array<Any>> {
            return Note.values().map {
                arrayOf(
                    it, when (it) {
                        Note.C -> "C"
                        Note.C_SHARP_D_FLAT -> "C_SHARP_D_FLAT"
                        Note.D -> "D"
                        Note.D_SHARP_E_FLAT -> "D_SHARP_E_FLAT"
                        Note.E -> "E"
                        Note.F -> "F"
                        Note.F_SHARP_G_FLAT -> "F_SHARP_G_FLAT"
                        Note.G -> "G"
                        Note.G_SHARP_A_FLAT -> "G_SHARP_A_FLAT"
                        Note.A -> "A"
                        Note.A_SHARP_B_FLAT -> "A_SHARP_B_FLAT"
                        Note.B -> "B"
                    }
                )
            }.toTypedArray()
        }
    }

    @Test
    fun `it maps from a note to a string correctly`() {
        val result = note.toSharedPreferencesString()

        assertThat(result).isEqualTo(
            noteString
        )
    }

    @Test
    fun `it parses a string to a note correctly`() {
        val result = parseNoteFromSharedPreferencesString(noteString)

        assertThat(result).isEqualTo(
            note
        )
    }
}