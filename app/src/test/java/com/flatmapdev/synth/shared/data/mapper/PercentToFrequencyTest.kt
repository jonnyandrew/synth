package com.flatmapdev.synth.shared.core.model

import com.flatmapdev.synth.shared.data.mapper.toFrequency
import com.flatmapdev.synth.shared.data.mapper.toPercent
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PercentToFrequencyTest(
    private val percent: Percent,
    private val frequency: Float
) {
    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun data(): Array<Array<Any>> = arrayOf(
            arrayOf(0, 100f),
            arrayOf(25, 177.73438f),
            arrayOf(50, 1343.75f),
            arrayOf(75, 6396.4844f),
            arrayOf(100, 20000f)
        )
    }

    @Test
    fun `it converts percent to frequency`() {
        val result = percent.toFrequency()

        assertThat(result).isEqualTo(Frequency(frequency))
    }

    @Test
    fun `it converts frequency to percent`() {
        val result = Frequency(frequency).toPercent()

        assertThat(result).isEqualTo(percent)
    }
}
