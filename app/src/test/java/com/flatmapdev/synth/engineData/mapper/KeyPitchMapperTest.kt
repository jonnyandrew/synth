package com.flatmapdev.synth.engineData.mapper

import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class KeyPitchMapperTest(
    private val key: Key,
    private val expectedPitch: Int
) {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(Key(Note.C, -1), 0),
                arrayOf(Key(Note.C_SHARP_D_FLAT, -1), 1),
                arrayOf(Key(Note.B, 3), 59),
                arrayOf(Key(Note.B, 3), 59),
                arrayOf(Key(Note.C, 4), 60),
                arrayOf(Key(Note.C_SHARP_D_FLAT, 4), 61),
                arrayOf(Key(Note.D, 4), 62),
                arrayOf(Key(Note.D_SHARP_E_FLAT, 4), 63),
                arrayOf(Key(Note.E, 4), 64),
                arrayOf(Key(Note.F, 4), 65),
                arrayOf(Key(Note.F_SHARP_G_FLAT, 4), 66),
                arrayOf(Key(Note.G, 4), 67),
                arrayOf(Key(Note.G_SHARP_A_FLAT, 4), 68),
                arrayOf(Key(Note.A, 4), 69),
                arrayOf(Key(Note.A_SHARP_B_FLAT, 4), 70),
                arrayOf(Key(Note.B, 4), 71),
                arrayOf(Key(Note.C, 5), 72),
                arrayOf(Key(Note.F_SHARP_G_FLAT, 9), 126),
                arrayOf(Key(Note.G, 9), 127),
                // A9 really equals pitch 128 but we constrain values to the range 0 to 127
                arrayOf(Key(Note.A, 9), 127),
                // B-2 really equals pitch -1 but we onstrain values outside the range 0 to 127
                arrayOf(Key(Note.B, -2), 0)
            )
        }
    }

    @Test
    fun `it converts the key to the correct pitch`() {
        val result = key.toPitch()

        assertThat(result).isEqualTo(expectedPitch)
    }
}