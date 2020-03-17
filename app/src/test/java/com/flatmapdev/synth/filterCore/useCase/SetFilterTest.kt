package com.flatmapdev.synth.filterCore.useCase

import com.flatmapdev.synth.doubles.filter.adapter.FakeFilterAdapter
import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.shared.core.model.Frequency
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SetFilterTest {
    private lateinit var filterAdapter: FilterAdapter

    @Before
    fun setUp() {
        filterAdapter = spyk(FakeFilterAdapter())
    }

    @Test
    fun `setCutoff sets the cutoff on the adapter`() {
        val frequency = Frequency(12345F)
        val subject = createSubject()

        subject.setCutoff(frequency)

        verify {
            filterAdapter.setCutoff(frequency)
        }
    }

    @Test
    fun `setResonance sets the resonance on the adapter`() {
        val subject = createSubject()

        subject.setResonance(25)

        verify {
            filterAdapter.setResonance(25)
        }
    }

    private fun createSubject(): SetFilter {
        return SetFilter(filterAdapter)
    }
}
