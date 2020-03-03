package com.flatmapdev.synth.oscillatorCore.useCase

import com.flatmapdev.synth.doubles.oscillator.adapter.FakeOscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class SetOscillatorTest {
    lateinit var oscillatorAdapters: Map<OscillatorId, OscillatorAdapter>
    @Test
    fun `it sets the oscillator on the adapter`() {
        val oscillator = Oscillator(2)
        val spyOscillatorAdapter = spyk(FakeOscillatorAdapter())
        oscillatorAdapters = mapOf(
            OscillatorId.Osc1 to spyOscillatorAdapter
        )
        val subject = createSubject()

        subject.execute(OscillatorId.Osc1, oscillator)

        verify {
            spyOscillatorAdapter.oscillator = oscillator
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `it throws if the adapter is not present`() {
        val oscillator = Oscillator(2)
        oscillatorAdapters = mapOf()
        val subject = createSubject()

        subject.execute(OscillatorId.Osc1, oscillator)
    }

    private fun createSubject(): SetOscillator {
        return SetOscillator(
            oscillatorAdapters
        )
    }
}
