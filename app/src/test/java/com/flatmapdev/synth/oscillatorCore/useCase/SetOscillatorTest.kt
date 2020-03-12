package com.flatmapdev.synth.oscillatorCore.useCase

import com.flatmapdev.synth.doubles.oscillator.adapter.FakeOscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class SetOscillatorTest {
    lateinit var oscillatorAdapters: Map<OscillatorId, OscillatorAdapter>
    @Test
    fun `setOscillator sets the oscillator on the adapter`() {
        val oscillator = Oscillator(2, Waveform.Triangle)
        val spyOscillatorAdapter = spyk(FakeOscillatorAdapter())
        oscillatorAdapters = mapOf(
            OscillatorId.Osc1 to spyOscillatorAdapter
        )
        val subject = createSubject()

        subject.setOscillator(OscillatorId.Osc1, oscillator)

        verify {
            spyOscillatorAdapter.oscillator = oscillator
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `setOscillator throws if the adapter is not present`() {
        val oscillator = Oscillator(2, Waveform.Noise)
        oscillatorAdapters = mapOf()
        val subject = createSubject()

        subject.setOscillator(OscillatorId.Osc1, oscillator)
    }

    @Test
    fun `setWaveform sets the waveform on the adapter`() {
        val waveform = Waveform.Sine
        val spyOscillatorAdapter = spyk(FakeOscillatorAdapter())
        oscillatorAdapters = mapOf(
            OscillatorId.Osc1 to spyOscillatorAdapter
        )
        val subject = createSubject()

        subject.setWaveform(OscillatorId.Osc1, waveform)

        verify {
            spyOscillatorAdapter.setWaveform(waveform)
        }
    }

    @Test
    fun `setPitchOffset sets the pitch offset on the adapter`() {
        val pitchOffset = 30
        val spyOscillatorAdapter = spyk(FakeOscillatorAdapter())
        oscillatorAdapters = mapOf(
            OscillatorId.Osc1 to spyOscillatorAdapter
        )
        val subject = createSubject()

        subject.setPitchOffset(OscillatorId.Osc1, pitchOffset)

        verify {
            spyOscillatorAdapter.setPitchOffset(pitchOffset)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `setWaveform throws if the adapter is not present`() {
        val waveform = Waveform.Sine
        oscillatorAdapters = mapOf()
        val subject = createSubject()

        subject.setWaveform(OscillatorId.Osc1, waveform)
    }

    private fun createSubject(): SetOscillator {
        return SetOscillator(
            oscillatorAdapters
        )
    }
}
