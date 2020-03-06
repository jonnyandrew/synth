package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import com.flatmapdev.synth.utils.test
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class GetScaleTest {
    @Test
    fun `when there is a stored scale, it gets the scale`() = runBlockingTest {
        val scale = Scale(Note.C, ScaleType.Minor)
        val scaleAdapter = FakeScaleAdapter(scale = scale)
        val subject = GetScale(scaleAdapter)

        subject.execute()
            .test(this)
            .assertValues(scale)
            .finish()
    }

    @Test
    fun `when there is no stored scale, it returns a default scale`() = runBlockingTest {
        val scaleAdapter = FakeScaleAdapter(scale = null)
        val subject = GetScale(scaleAdapter)

        subject.execute()
            .test(this)
            .assertValues(
                Scale(Note.C, ScaleType.Pentatonic)
            )
            .finish()
    }
}
