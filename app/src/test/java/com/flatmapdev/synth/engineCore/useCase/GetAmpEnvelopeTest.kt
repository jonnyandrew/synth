package com.flatmapdev.synth.engineCore.useCase

import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import io.mockk.spyk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetAmpEnvelopeTest {
    @Test
    fun `it gets the amp envelope`() {
        val envelope = Envelope(23, 34, 45, 56)
        val synthEngineAdapter = spyk(
            StubSynthEngineAdapter(
                ampEnvelope = envelope
            )
        )
        val subject = GetAmpEnvelope(synthEngineAdapter)

        val result = subject.execute()

        assertThat(result).isEqualTo(envelope)
    }
}