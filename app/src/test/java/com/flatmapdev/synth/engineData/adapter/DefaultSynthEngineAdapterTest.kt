package com.flatmapdev.synth.engineData.adapter

import com.flatmapdev.synth.doubles.jni.FakeSynth
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.engineData.mapper.toFloatArray
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DefaultSynthEngineAdapterTest {
    @Test
    fun `it gets the version`() {
        val synth = FakeSynth(version = "345.678")
        val subject = DefaultSynthEngineAdapter(synth)

        val result = subject.getVersion()

        assertThat(result).isEqualTo("345.678")
    }

    @Test
    fun `it starts the synth`() {
        val synth = spyk(FakeSynth())
        val subject = DefaultSynthEngineAdapter(synth)

        subject.start()

        verify { synth.start() }
    }

    @Test
    fun `it stops the synth`() {
        val synth = spyk(FakeSynth())
        val subject = DefaultSynthEngineAdapter(synth)

        subject.stop()

        verify { synth.stop() }
    }

    @Test
    fun `it plays the correct keys on the synth`() {
        val synth = spyk(FakeSynth())
        val subject = DefaultSynthEngineAdapter(synth)

        subject.playNote(key = Key(Note.C, 4))
        subject.playNote(key = Key(Note.D, 4))
        subject.playNote(key = Key(Note.E, 4))
        subject.playNote(key = Key(Note.F, 4))

        verifyOrder {
            synth.playNote(60)
            synth.playNote(62)
            synth.playNote(64)
            synth.playNote(65)
        }
    }

    @Test
    fun `it stops the correct keys on the synth`() {
        val synth = spyk(FakeSynth())
        val subject = DefaultSynthEngineAdapter(synth)

        subject.playNote(key = Key(Note.C, 4))
        subject.stopNote()
        subject.playNote(key = Key(Note.E, 4))
        subject.stopNote()

        verifyOrder {
            synth.playNote(60)
            synth.stopNote()
            synth.playNote(64)
            synth.stopNote()
        }
    }

    @Test
    fun `it sets the correct amp envelope on the synth`() {
        val synth = spyk(FakeSynth())
        val subject = DefaultSynthEngineAdapter(synth)
        val envelope = Envelope(10, 20, 30, 40)

        subject.ampEnvelope = envelope

        verify {
            synth.setAmpEnvelope(envelope.toFloatArray())
        }
    }

    @Test
    fun `it gets the correct amp envelope from the synth`() {
        val envelope = Envelope(10, 20, 30, 40)
        val synth = spyk(
            FakeSynth(
                ampEnvelope = envelope.toFloatArray()
            )
        )
        val subject = DefaultSynthEngineAdapter(synth)

        val result = subject.ampEnvelope

        assertThat(result).isEqualTo(envelope)
    }
}