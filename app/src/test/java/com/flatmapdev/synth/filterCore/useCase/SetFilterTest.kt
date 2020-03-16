package com.flatmapdev.synth.filterCore.useCase

import com.flatmapdev.synth.doubles.filter.adapter.FakeFilterAdapter
import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
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
        val subject = createSubject()

        subject.setCutoff(12345F)

        verify {
            filterAdapter.setCutoff(12345F)
        }
    }

    @Test
    fun `setCutoff turns off the filter when cutoff is gt 20000`() {
        val subject = createSubject()

        subject.setCutoff(20001F)
        subject.setCutoff(Float.MAX_VALUE)

        verify {
            filterAdapter.setIsActive(false)
            filterAdapter.setIsActive(false)
        }
    }

    @Test
    fun `setCutoff turns on the filter when cutoff is lte 20000`() {
        val subject = createSubject()

        subject.setCutoff(Float.MIN_VALUE)
        subject.setCutoff(20000F)

        verify {
            filterAdapter.setIsActive(true)
            filterAdapter.setIsActive(true)
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
