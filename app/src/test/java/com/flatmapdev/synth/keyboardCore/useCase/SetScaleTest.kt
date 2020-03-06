package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class SetScaleTest {
    @Test
    fun `it sets the scale`() {
        val scale = Scale(Note.G_SHARP_A_FLAT, ScaleType.Pentatonic)
        val spyScaleAdapter = spyk(FakeScaleAdapter(scale = scale))
        val subject = SetScale(spyScaleAdapter)

        subject.execute(scale)

        verify {
            spyScaleAdapter.storeScale(scale)
        }
    }
}
