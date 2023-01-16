package com.flatmapdev.synth.oscillatorCore.useCase

import com.flatmapdev.synth.doubles.oscillator.adapter.FakeOscillatorAdapter
import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetOscillatorTest {
    lateinit var oscillatorAdapters: Map<OscillatorId, OscillatorAdapter>
    @Test
    fun `it returns an oscillator from the adapter`() {
        val oscillator = createOscillator(-12)
        oscillatorAdapters = mapOf(
            OscillatorId.Osc1 to
                FakeOscillatorAdapter(
                    oscillator = oscillator
                )
        )
        val subject = createSubject()

        val result = subject.execute(OscillatorId.Osc1)

        assertThat(result).isEqualTo(oscillator)
    }

    @Test(expected = IllegalStateException::class)
    fun `it throws if the adapter is not present`() {
        oscillatorAdapters = mapOf()
        val subject = createSubject()

        subject.execute(OscillatorId.Osc1)
    }

    private fun createSubject(): GetOscillator {
        return GetOscillator(
            oscillatorAdapters
        )
    }
}
