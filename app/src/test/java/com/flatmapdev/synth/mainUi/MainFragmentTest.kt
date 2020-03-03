package com.flatmapdev.synth.mainUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynth
import com.flatmapdev.synth.doubles.jni.FakeSynthOscillator
import io.mockk.spyk
import io.mockk.verify
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `it shows the keyboard`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.keyboard))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when a key is tapped, it sends a signal to the synth engine`() {
        val spySynth = spyk(FakeSynth())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(
                    synth = spySynth
                )
            )
            .build()
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.keyboard))
            .perform(click())

        verify { spySynth.playNote(any()) }
    }

    @Test
    fun `when a amp envelope is changed, it sends the new envelope to the synth engine`() {
        val spySynth = spyk(FakeSynth())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synth = spySynth)
            )
            .build()
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.ampEnvelopeControlsAttackSeekBar))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.ampEnvelopeControlsDecaySeekBar))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.ampEnvelopeControlsSustainSeekBar))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.ampEnvelopeControlsReleaseSeekBar))
            .perform(scrollTo())
            .perform(click())

        verify(exactly = 4) {
            spySynth.setAmpEnvelope(any())
        }
    }

    @Test
    fun `it displays the amp envelope title`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope attack control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_attack))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsAttackSeekBar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope decay control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_decay))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsDecaySeekBar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope sustain control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_sustain))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsSustainSeekBar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope release control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_sustain))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsReleaseSeekBar))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the oscillator 1 control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()
        val expectedString = getApp().getString(R.string.osc_title, 1)

        onView(
            allOf(
                isDescendantOfA(withId(R.id.osc1Controls)),
                withText(expectedString)
            )
        )
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.osc1Controls)),
                withId(R.id.oscControlsPitchSeekBar)
            )
        )
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the oscillator 2 control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()
        val expectedString = getApp().getString(R.string.osc_title, 2)

        onView(
            allOf(
                isDescendantOfA(withId(R.id.osc2Controls)),
                withText(expectedString)
            )
        )
            .perform(scrollTo())
            .check(matches(isDisplayed()))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.osc2Controls)),
                withId(R.id.oscControlsPitchSeekBar)
            )
        )
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when a oscillator 1 pitch is changed, it sends the new oscillator config to the synth engine`() {
        val spySynthOscillator = spyk(FakeSynthOscillator())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthOscillator1 = spySynthOscillator)
            )
            .build()
        launchFragmentInContainer<MainFragment>()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.osc1Controls)),
                withId(R.id.oscControlsPitchSeekBar)
            )
        )
            .perform(scrollTo())
            .perform(swipeRight())

        verify(atLeast = 1) {
            spySynthOscillator.setOscillator(any())
        }
    }

    @Test
    fun `when a oscillator 2 pitch is changed, it sends the new oscillator config to the synth engine`() {
        val spySynthOscillator = spyk(FakeSynthOscillator())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthOscillator2 = spySynthOscillator)
            )
            .build()
        launchFragmentInContainer<MainFragment>()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.osc2Controls)),
                withId(R.id.oscControlsPitchSeekBar)
            )
        )
            .perform(scrollTo())
            .perform(swipeRight())

        verify(atLeast = 1) {
            spySynthOscillator.setOscillator(any())
        }
    }
}
