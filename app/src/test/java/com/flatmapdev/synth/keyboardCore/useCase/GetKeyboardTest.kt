package com.flatmapdev.synth.keyboardCore.useCase

import app.cash.turbine.test
import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class GetKeyboardTest(
    private val scale: Scale,
    private val keys: List<Key>
) {

    @Test
    fun `it gets the expected keys`() = runTest {
        val subject = GetKeyboard(
            GetScale(
                FakeScaleAdapter(scale = scale)
            )
        )

        subject.execute().test {
            assertThat(awaitItem()).isEqualTo(keys)
        }
    }

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun data(): Array<Array<Any>> {
            return arrayOf(
                arrayOf(
                    Scale(Note.C, ScaleType.Major),
                    listOf(
                        Key(Note.C, 3),
                        Key(Note.D, 3),
                        Key(Note.E, 3),
                        Key(Note.F, 3),
                        Key(Note.G, 3),
                        Key(Note.A, 3),
                        Key(Note.B, 3),
                        Key(Note.C, 4),
                        Key(Note.D, 4),
                        Key(Note.E, 4),
                        Key(Note.F, 4),
                        Key(Note.G, 4),
                        Key(Note.A, 4),
                        Key(Note.B, 4),
                        Key(Note.C, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.C, ScaleType.HarmonicMinor),
                    listOf(
                        Key(Note.C, 3),
                        Key(Note.D, 3),
                        Key(Note.D_SHARP_E_FLAT, 3),
                        Key(Note.F, 3),
                        Key(Note.G, 3),
                        Key(Note.G_SHARP_A_FLAT, 3),
                        Key(Note.B, 3),
                        Key(Note.C, 4),
                        Key(Note.D, 4),
                        Key(Note.D_SHARP_E_FLAT, 4),
                        Key(Note.F, 4),
                        Key(Note.G, 4),
                        Key(Note.G_SHARP_A_FLAT, 4),
                        Key(Note.B, 4),
                        Key(Note.C, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.D, ScaleType.Major),
                    listOf(
                        Key(Note.D, 3),
                        Key(Note.E, 3),
                        Key(Note.F_SHARP_G_FLAT, 3),
                        Key(Note.G, 3),
                        Key(Note.A, 3),
                        Key(Note.B, 3),
                        Key(Note.C_SHARP_D_FLAT, 4),
                        Key(Note.D, 4),
                        Key(Note.E, 4),
                        Key(Note.F_SHARP_G_FLAT, 4),
                        Key(Note.G, 4),
                        Key(Note.A, 4),
                        Key(Note.B, 4),
                        Key(Note.C_SHARP_D_FLAT, 5),
                        Key(Note.D, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.F, ScaleType.HarmonicMinor),
                    listOf(
                        Key(Note.F, 3),
                        Key(Note.G, 3),
                        Key(Note.G_SHARP_A_FLAT, 3),
                        Key(Note.A_SHARP_B_FLAT, 3),
                        Key(Note.C, 4),
                        Key(Note.C_SHARP_D_FLAT, 4),
                        Key(Note.E, 4),
                        Key(Note.F, 4),
                        Key(Note.G, 4),
                        Key(Note.G_SHARP_A_FLAT, 4),
                        Key(Note.A_SHARP_B_FLAT, 4),
                        Key(Note.C, 5),
                        Key(Note.C_SHARP_D_FLAT, 5),
                        Key(Note.E, 5),
                        Key(Note.F, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.B, ScaleType.Major),
                    listOf(
                        Key(Note.B, 3),
                        Key(Note.C_SHARP_D_FLAT, 4),
                        Key(Note.D_SHARP_E_FLAT, 4),
                        Key(Note.E, 4),
                        Key(Note.F_SHARP_G_FLAT, 4),
                        Key(Note.G_SHARP_A_FLAT, 4),
                        Key(Note.A_SHARP_B_FLAT, 4),
                        Key(Note.B, 4),
                        Key(Note.C_SHARP_D_FLAT, 5),
                        Key(Note.D_SHARP_E_FLAT, 5),
                        Key(Note.E, 5),
                        Key(Note.F_SHARP_G_FLAT, 5),
                        Key(Note.G_SHARP_A_FLAT, 5),
                        Key(Note.A_SHARP_B_FLAT, 5),
                        Key(Note.B, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.B, ScaleType.HarmonicMinor),
                    listOf(
                        Key(Note.B, 3),
                        Key(Note.C_SHARP_D_FLAT, 4),
                        Key(Note.D, 4),
                        Key(Note.E, 4),
                        Key(Note.F_SHARP_G_FLAT, 4),
                        Key(Note.G, 4),
                        Key(Note.A_SHARP_B_FLAT, 4),
                        Key(Note.B, 4),
                        Key(Note.C_SHARP_D_FLAT, 5),
                        Key(Note.D, 5),
                        Key(Note.E, 5),
                        Key(Note.F_SHARP_G_FLAT, 5),
                        Key(Note.G, 5),
                        Key(Note.A_SHARP_B_FLAT, 5),
                        Key(Note.B, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.C, ScaleType.MinorPentatonic),
                    listOf(
                        Key(Note.C, 3),
                        Key(Note.D_SHARP_E_FLAT, 3),
                        Key(Note.F, 3),
                        Key(Note.G, 3),
                        Key(Note.A_SHARP_B_FLAT, 3),
                        Key(Note.C, 4),
                        Key(Note.D_SHARP_E_FLAT, 4),
                        Key(Note.F, 4),
                        Key(Note.G, 4),
                        Key(Note.A_SHARP_B_FLAT, 4),
                        Key(Note.C, 5)
                    )
                ),
                arrayOf(
                    Scale(Note.B, ScaleType.MinorPentatonic),
                    listOf(
                        Key(Note.B, 3),
                        Key(Note.D, 4),
                        Key(Note.E, 4),
                        Key(Note.F_SHARP_G_FLAT, 4),
                        Key(Note.A, 4),
                        Key(Note.B, 4),
                        Key(Note.D, 5),
                        Key(Note.E, 5),
                        Key(Note.F_SHARP_G_FLAT, 5),
                        Key(Note.A, 5),
                        Key(Note.B, 5)
                    )
                )
            )
        }
    }
}
