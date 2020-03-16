package com.flatmapdev.synth.filterData.adapter

import com.flatmapdev.synth.doubles.jni.FakeSynthFilter
import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.filterCore.model.Filter
import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.jni.SynthFilter
import com.flatmapdev.synth.shared.core.model.toFraction
import com.flatmapdev.synth.shared.core.model.toPercent
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DefaultFilterAdapterTest {
    private lateinit var synthFilter: SynthFilter

    @Test
    fun `getFilter gets the filter data`() {
        val filterData = FilterData(
            cutoffFrequency = 234f,
            resonance = 0.5f
        )
        synthFilter = FakeSynthFilter(
            filterData = filterData
        )
        val subject = createSubject()

        val result = subject.getFilter()

        assertThat(result).isEqualTo(
            Filter(
                234f,
                0.5f.toPercent()
            )
        )
    }

    @Test
    fun `setActive sets the value on the synth filter`() {
        synthFilter = spyk(FakeSynthFilter())
        val subject = createSubject()

        subject.setIsActive(false)

        verify {
            synthFilter.setIsActive(false)
        }
    }

    @Test
    fun `setCutoff sets the cutoff on the synth filter`() {
        synthFilter = spyk(FakeSynthFilter())
        val subject = createSubject()

        subject.setCutoff(10000F)

        verify {
            synthFilter.setCutoff(10000F)
        }
    }

    @Test
    fun `setResonance sets the resonance on the synth filter`() {
        synthFilter = spyk(FakeSynthFilter())
        val subject = createSubject()

        subject.setResonance(50)

        verify {
            synthFilter.setResonance(50.toFraction())
        }
    }

    private fun createSubject(): FilterAdapter {
        return DefaultFilterAdapter(
            synthFilter
        )
    }
}
