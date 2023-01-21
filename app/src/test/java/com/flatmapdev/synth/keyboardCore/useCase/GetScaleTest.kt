package com.flatmapdev.synth.keyboardCore.useCase

import app.cash.turbine.test
import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetScaleTest {
    @Test
    fun `when there is a stored scale, it gets the scale`() = runTest {
        val scale = Scale(Note.C, ScaleType.HarmonicMinor)
        val scaleAdapter = FakeScaleAdapter(scale = scale)
        val subject = GetScale(scaleAdapter)

        subject.execute().test {
            assertThat(awaitItem()).isEqualTo(scale)
        }
    }

    @Test
    fun `when there is no stored scale, it returns a default scale`() = runTest {
        val scaleAdapter = FakeScaleAdapter(scale = null)
        val subject = GetScale(scaleAdapter)

        subject.execute().test {
            assertThat(awaitItem()).isEqualTo(
                Scale(Note.C, ScaleType.MinorPentatonic)
            )
        }
    }
}
