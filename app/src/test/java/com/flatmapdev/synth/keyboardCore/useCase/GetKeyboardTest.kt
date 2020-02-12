package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetKeyboardTest {

    @Test
    fun `it gets the expected keys`() {
        val subject = GetKeyboard()

        val result = subject.execute()

        assertThat(result).isEqualTo(
            listOf(
                Key(Note.C, 4),
                Key(Note.D, 4),
                Key(Note.E, 4)
            )
        )
    }
}