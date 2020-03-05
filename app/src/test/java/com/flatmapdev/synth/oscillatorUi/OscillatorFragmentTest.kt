package com.flatmapdev.synth.oscillatorUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynthOscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OscillatorFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `it displays the oscillator control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<OscillatorFragment>(
            OscillatorFragmentArgs(OscillatorId.Osc1).toBundle()
        )
        val expectedString = getApp().getString(R.string.osc_title, 1)

        onView(withText(expectedString))
            .check(matches(isDisplayed()))
        onView(withId(R.id.oscControlsPitchSeekBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when the oscillator pitch is changed, it sends the new oscillator config to the synth engine`() {
        val spySynthOscillator = spyk(FakeSynthOscillator())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthOscillator1 = spySynthOscillator)
            )
            .build()
        launchFragmentInContainer<OscillatorFragment>(
            OscillatorFragmentArgs(OscillatorId.Osc1).toBundle()
        )

        onView(withId(R.id.oscControlsPitchSeekBar))
            .perform(swipeRight())

        verify(atLeast = 1) {
            spySynthOscillator.setOscillator(any())
        }
    }

    @Test
    fun `when the oscillator pitch is changed, it doesn't update the wrong oscillator in the engine`() {
        val spySynthOscillator = spyk(FakeSynthOscillator())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthOscillator2 = spySynthOscillator)
            )
            .build()
        launchFragmentInContainer<OscillatorFragment>(
            OscillatorFragmentArgs(OscillatorId.Osc1).toBundle()
        )

        onView(withId(R.id.oscControlsPitchSeekBar))
            .perform(swipeRight())

        verify(inverse = true) {
            spySynthOscillator.setOscillator(any())
        }
    }
}
