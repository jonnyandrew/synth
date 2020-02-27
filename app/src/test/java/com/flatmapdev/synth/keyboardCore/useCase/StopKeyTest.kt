package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class StopKeyTest {
    @SpyK
    private var spyEngineAdapter = StubSynthEngineAdapter()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `it sends a signal to the engine`() {
        val subject = StopKeys(spyEngineAdapter)

        subject.execute()

        verify { spyEngineAdapter.stopNote() }
    }
}
