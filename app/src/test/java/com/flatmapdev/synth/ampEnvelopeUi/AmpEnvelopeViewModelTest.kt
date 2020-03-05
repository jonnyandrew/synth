package com.flatmapdev.synth.ampEnvelopeUi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.engineCore.useCase.GetAmpEnvelope
import com.flatmapdev.synth.engineCore.useCase.SetAmpEnvelope
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AmpEnvelopeViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var stubSynthEngineAdapter: StubSynthEngineAdapter
    private lateinit var getAmpEnvelope: GetAmpEnvelope
    private lateinit var spySetAmpEnvelope: SetAmpEnvelope

    @Before
    fun setUp() {
        stubSynthEngineAdapter = StubSynthEngineAdapter()
    }

    @Test
    fun `it emits the the amp envelope`() {
        val envelope = Envelope(40, 50, 60, 80)
        stubSynthEngineAdapter = StubSynthEngineAdapter(
            ampEnvelope = envelope
        )
        val subject = createSubject()

        subject.init()

        assertThat(subject.ampEnvelope.value)
            .isEqualTo(envelope)
    }

    @Test
    fun `setAmpEnvelope should call the use case`() {
        val envelope = Envelope(40, 50, 60, 80)
        val subject = createSubject()

        subject.setAmpEnvelope(envelope)

        verify { spySetAmpEnvelope.execute(envelope) }
    }

    private fun createSubject(): AmpEnvelopeViewModel {
        getAmpEnvelope = GetAmpEnvelope(stubSynthEngineAdapter)
        spySetAmpEnvelope = spyk(SetAmpEnvelope(stubSynthEngineAdapter))
        return AmpEnvelopeViewModel(
            getAmpEnvelope,
            spySetAmpEnvelope
        )
    }
}
