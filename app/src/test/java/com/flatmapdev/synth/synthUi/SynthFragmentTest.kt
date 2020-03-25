package com.flatmapdev.synth.synthUi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynthEngine
import com.flatmapdev.synth.utils.launchAndSetUpFragment
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SynthFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `it shows the keyboard`() {
        getApp().appComponent = testComponentBuilder.build()
        launchAndSetUpFragment<SynthFragment>()

        onView(withId(R.id.keyboard))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when a key is tapped, it sends a signal to the synth engine`() {
        val spySynth = spyk(FakeSynthEngine())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(
                    synthEngine = spySynth
                )
            )
            .build()
        launchAndSetUpFragment<SynthFragment>()

        onView(withId(R.id.keyboard))
            .perform(click())

        verify { spySynth.playNote(any(), any()) }
    }
}
