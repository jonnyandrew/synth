package com.flatmapdev.synth.oscillatorData.adapter

import com.flatmapdev.synth.doubles.jni.FakeSynthOscillator
import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import com.flatmapdev.synth.oscillatorData.mapper.toDataModel
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DefaultOscillatorAdapterTest {
    lateinit var synthOscillator: SynthOscillator

    @Test
    fun `getOscillator gets the oscillator data from the synth oscillator`() {
        val oscillator = createOscillator(12)
        synthOscillator = FakeSynthOscillator(
            oscillator.toDataModel()
        )
        val subject = createSubject()

        val result = subject.oscillator

        assertThat(result).isEqualTo(oscillator)
    }

    @Test
    fun `setOscillator sets the oscillator data on the synth oscillator`() {
        val oscillator = createOscillator(13)
        synthOscillator = spyk(FakeSynthOscillator())
        val subject = createSubject()

        subject.oscillator = oscillator

        verify {
            synthOscillator.setOscillator(oscillator.toDataModel())
        }
    }

    @Test
    fun `setWaveform sets the waveform on the synth oscillator`() {
        val waveform = Waveform.Square
        synthOscillator = spyk(FakeSynthOscillator())
        val subject = createSubject()

        subject.setWaveform(waveform)

        verify {
            synthOscillator.setWaveform(waveform.toDataModel())
        }
    }

    fun `setPitchOffset sets the pitch offset on the synth oscillator`() {
        val pitchOffset = -3
        synthOscillator = FakeSynthOscillator()
        val subject = createSubject()

        subject.setPitchOffset(pitchOffset)

        verify {
            synthOscillator.setPitchOffset(pitchOffset)
        }
    }

    private fun createSubject(): DefaultOscillatorAdapter {
        return DefaultOscillatorAdapter(
            synthOscillator
        )
    }
}
