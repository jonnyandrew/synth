package com.flatmapdev.synth.mainUi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.useCase.GetKeyboard
import com.flatmapdev.synth.keyboardCore.useCase.PlayKey
import com.flatmapdev.synth.keyboardCore.useCase.StopKeys
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var stubSynthEngineAdapter: StubSynthEngineAdapter
    private lateinit var spyPlayKey: PlayKey
    private lateinit var spyStopKeys: StopKeys

    @Before
    fun setUp() {
        stubSynthEngineAdapter = StubSynthEngineAdapter()
        spyPlayKey = spyk(PlayKey(stubSynthEngineAdapter))
        spyStopKeys = spyk(StopKeys(stubSynthEngineAdapter))
    }

    @Test
    fun `it emits the keyboard`() {
        val subject = createSubject()

        subject.init()

        assertThat(subject.keyboard.value)
            .isEqualTo(
                listOf(
                    Key(Note.C, 3),
                    Key(Note.D, 3),
                    Key(Note.E, 3),
                    Key(Note.F, 3),
                    Key(Note.G, 3),
                    Key(Note.A, 3),
                    Key(Note.B, 3),
                    Key(Note.C, 4),
                    Key(Note.D, 4),
                    Key(Note.E, 4),
                    Key(Note.F, 4),
                    Key(Note.G, 4),
                    Key(Note.A, 4),
                    Key(Note.B, 4),
                    Key(Note.C, 5)
                )
            )
    }

    @Test
    fun `playKey should call the use case`() {
        val key = Key(Note.G_SHARP_A_FLAT, 2)
        val subject = createSubject()

        subject.playKey(key)

        verify { spyPlayKey.execute(key) }
    }

    @Test
    fun `stopKeys should call the use case`() {
        val subject = createSubject()

        subject.stopKeys()

        verify { spyStopKeys.execute() }
    }

    private fun createSubject(): MainViewModel {
        return MainViewModel(
            GetKeyboard(),
            spyPlayKey,
            spyStopKeys
        )
    }
}
