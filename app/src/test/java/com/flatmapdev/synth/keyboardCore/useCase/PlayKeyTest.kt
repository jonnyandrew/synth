package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class PlayKeyTest {
    @SpyK
    private var spyEngineAdapter = StubSynthEngineAdapter()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `it sends a signal to the engine`() {
        val subject = PlayKey(spyEngineAdapter)
        val key = Key(Note.C, 6)

        subject.execute(key)
        verify { spyEngineAdapter.playNote(key) }
    }
}