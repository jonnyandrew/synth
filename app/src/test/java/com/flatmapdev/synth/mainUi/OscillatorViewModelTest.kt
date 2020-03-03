package com.flatmapdev.synth.mainUi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flatmapdev.synth.doubles.oscillator.adapter.FakeOscillatorAdapter
import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.oscillatorCore.useCase.GetOscillator
import com.flatmapdev.synth.oscillatorCore.useCase.SetOscillator
import io.mockk.spyk
import io.mockk.verifyAll
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class OscillatorViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var fakeOscillator1Adapter: FakeOscillatorAdapter
    private lateinit var fakeOscillator2Adapter: FakeOscillatorAdapter
    private lateinit var getOscillator: GetOscillator
    private lateinit var spySetOscillator: SetOscillator

    @Before
    fun setUp() {
        fakeOscillator1Adapter = FakeOscillatorAdapter()
        fakeOscillator2Adapter = FakeOscillatorAdapter()
    }

    @Test
    fun `it emits the the oscillators`() {
        val oscillator = createOscillator(-4)
        fakeOscillator1Adapter = FakeOscillatorAdapter(
            oscillator = oscillator
        )
        val subject = createSubject()

        subject.init()

        assertThat(subject.oscillator1.value)
            .isEqualTo(oscillator)
    }

    @Test
    fun `setOscillator should call the use case`() {
        val oscillator = createOscillator(4)
        val subject = createSubject()

        subject.setOscillator1(oscillator)
        subject.setOscillator2(oscillator)

        verifyAll {
            spySetOscillator.execute(OscillatorId.Osc1, oscillator)
            spySetOscillator.execute(OscillatorId.Osc2, oscillator)
        }
    }

    private fun createSubject(): OscillatorViewModel {
        val adapters = mapOf(
            OscillatorId.Osc1 to fakeOscillator1Adapter,
            OscillatorId.Osc2 to fakeOscillator2Adapter
        )
        getOscillator = GetOscillator(adapters)
        spySetOscillator = spyk(SetOscillator(adapters))
        return OscillatorViewModel(
            getOscillator,
            spySetOscillator
        )
    }
}
