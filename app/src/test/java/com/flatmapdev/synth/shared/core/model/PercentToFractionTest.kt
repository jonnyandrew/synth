package com.flatmapdev.synth.shared.core.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PercentToFractionTest(
    private val percent: Percent,
    private val fraction: Float
) {
    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun data(): Array<Array<Any>> = arrayOf(
            arrayOf(-1, -0.01F),
            arrayOf(0, 0F),
            arrayOf(67, 0.67F),
            arrayOf(100, 1.0F),
            arrayOf(250, 2.5F)
        )
    }

    @Test
    fun `it converts percent to fraction`() {
        val result = percent.toFraction()

        assertThat(result).isEqualTo(fraction)
    }

    @Test
    fun `it converts fraction to percent`() {
        val result = fraction.toPercent()

        assertThat(result).isEqualTo(percent)
    }
}
