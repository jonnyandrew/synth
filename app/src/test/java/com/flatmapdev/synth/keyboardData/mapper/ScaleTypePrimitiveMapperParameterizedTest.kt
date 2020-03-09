package com.flatmapdev.synth.keyboardData.mapper

import com.flatmapdev.synth.keyboardCore.model.ScaleType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ScaleTypePrimitiveMapperParameterizedTest(
    private val scaleType: ScaleType,
    private val scaleString: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Array<Array<Any>> {
            return ScaleType.values().map {
                arrayOf(
                    it, when (it) {
                        ScaleType.Major -> "MAJOR"
                        ScaleType.HarmonicMinor -> "HARMONIC_MINOR"
                        ScaleType.MinorPentatonic -> "MINOR_PENTATONIC"
                    }
                )
            }.toTypedArray()
        }
    }

    @Test
    fun `it maps from a ScaleType to a String correctly`() {
        val result = scaleType.toSharedPreferencesString()

        assertThat(result).isEqualTo(
            scaleString
        )
    }

    @Test
    fun `it parses a String to a ScaleType correctly`() {
        val result = parseScaleTypeFromSharedPreferencesString(scaleString)

        assertThat(result).isEqualTo(
            scaleType
        )
    }
}