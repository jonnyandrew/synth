package com.flatmapdev.synth.synthUi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.useCase.GetKeyboard
import com.flatmapdev.synth.keyboardCore.useCase.GetScale
import com.flatmapdev.synth.keyboardCore.useCase.PlayKey
import com.flatmapdev.synth.keyboardCore.useCase.StopKeys
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SynthViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var stubSynthEngineAdapter: StubSynthEngineAdapter
    private lateinit var spyPlayKey: PlayKey
    private lateinit var spyStopKeys: StopKeys

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        stubSynthEngineAdapter = StubSynthEngineAdapter()
        spyPlayKey = spyk(PlayKey(stubSynthEngineAdapter))
        spyStopKeys = spyk(StopKeys(stubSynthEngineAdapter))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `it emits the keyboard`() = runTest {
        val subject = createSubject()
        val testObserver = mockk<Observer<List<Key>>>(relaxed = true)

        subject.init()
        subject.keyboard
            .observeForever(testObserver)

        verify(exactly = 1) {
            testObserver.onChanged(any())
        }
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

    private fun createSubject(): SynthViewModel {
        return SynthViewModel(
            GetKeyboard(GetScale(FakeScaleAdapter())),
            spyPlayKey,
            spyStopKeys
        )
    }
}
