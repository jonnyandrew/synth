package com.flatmapdev.synth.filterCore.useCase

import com.flatmapdev.synth.doubles.filter.adapter.FakeFilterAdapter
import com.flatmapdev.synth.doubles.filter.model.createFilter
import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.shared.core.model.Frequency
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetFilterTest {
    private lateinit var filterAdapter: FilterAdapter

    @Test
    fun `it gets the filter`() {
        val filter = createFilter(Frequency(2000f), 4)
        filterAdapter = FakeFilterAdapter(filter = filter)
        val subject = createSubject()

        val result = subject.execute()

        assertThat(result).isEqualTo(filter)
    }

    private fun createSubject(): GetFilter {
        return GetFilter(filterAdapter)
    }
}
