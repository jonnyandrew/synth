package com.flatmapdev.synth.engineCore.useCase

import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class SetAmpEnvelopeTest {
    @Test
    fun `it gets the amp envelope`() {
        val envelope = Envelope(23, 34, 45, 56)
        val synthEngineAdapter = spyk(StubSynthEngineAdapter())
        val subject = SetAmpEnvelope(synthEngineAdapter)

        subject.execute(envelope)

        verify {
            synthEngineAdapter.ampEnvelope = envelope
        }
    }
}
