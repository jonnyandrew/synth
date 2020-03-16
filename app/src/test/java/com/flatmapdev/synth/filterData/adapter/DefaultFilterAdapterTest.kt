package com.flatmapdev.synth.filterData.adapter

import com.flatmapdev.synth.doubles.jni.FakeSynthFilter
import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.jni.SynthFilter
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class DefaultFilterAdapterTest {
    private lateinit var synthFilter: SynthFilter

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
            synthFilter.setResonance(2.0F)
        }
    }

    private fun createSubject(): FilterAdapter {
        return DefaultFilterAdapter(
            synthFilter
        )
    }
}
