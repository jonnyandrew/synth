package com.flatmapdev.synth.oscillatorData.adapter

import com.flatmapdev.synth.doubles.jni.FakeSynthOscillator
import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.jni.SynthOscillator
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DefaultOscillatorAdapterTest {
    lateinit var synthOscillator: SynthOscillator

    @Test
    fun `it gets the oscillator data from the synth oscillator`() {
        val oscillator = createOscillator(12)
        synthOscillator = FakeSynthOscillator(
            oscillator
        )
        val subject = createSubject()

        val result = subject.oscillator

        assertThat(result).isEqualTo(oscillator)
    }

    @Test
    fun `it sets the oscillator data on the synth oscillator`() {
        val oscillator = createOscillator(13)
        synthOscillator = spyk(FakeSynthOscillator())
        val subject = createSubject()

        subject.oscillator = oscillator

        verify {
            synthOscillator.setOscillator(oscillator)
        }
    }

    private fun createSubject(): DefaultOscillatorAdapter {
        return DefaultOscillatorAdapter(
            synthOscillator
        )
    }
}
