package com.flatmapdev.synth.filterUi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flatmapdev.synth.doubles.filter.adapter.FakeFilterAdapter
import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.filterCore.useCase.GetFilter
import com.flatmapdev.synth.filterCore.useCase.SetFilter
import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.data.mapper.toFrequency
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FilterViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var fakeFilterAdapter: FakeFilterAdapter
    private lateinit var spySetFilter: SetFilter

    @Before
    fun setUp() {
        fakeFilterAdapter = FakeFilterAdapter()
    }

    @Test
    fun `it emits the filter`() {
        val filter = Filter(
            cutoffFrequency = 1000f,
            resonance = 66
        )
        fakeFilterAdapter = FakeFilterAdapter(filter)
        val subject = createSubject()

        subject.init()

        assertThat(subject.filter.value)
            .isEqualTo(
                FilterViewModel.FilterControls(
                    46,
                    66
                )
            )
    }

    @Test
    fun `setCutoff should call the use case with the frequency`() {
        val cutoff: Percent = 10
        val cutoffFrequency = cutoff.toFrequency()
        val subject = createSubject()

        subject.setCutoff(cutoff)

        verify { spySetFilter.setCutoff(cutoffFrequency) }
    }

    @Test
    fun `setResonance should call the use case`() {
        val subject = createSubject()

        subject.setResonance(50)

        verify { spySetFilter.setResonance(50) }
    }

    private fun createSubject(): FilterViewModel {
        spySetFilter = spyk(SetFilter(fakeFilterAdapter))
        return FilterViewModel(
            GetFilter(fakeFilterAdapter),
            spySetFilter
        )
    }
}
