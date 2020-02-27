package com.flatmapdev.synth.mainUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynth
import io.mockk.spyk
import io.mockk.verify
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

        onView(withId(R.id.ampEnvelopeControlsAttackSeekBar)).perform(click())
        onView(withId(R.id.ampEnvelopeControlsDecaySeekBar)).perform(click())
        onView(withId(R.id.ampEnvelopeControlsSustainSeekBar)).perform(click())
        onView(withId(R.id.ampEnvelopeControlsReleaseSeekBar)).perform(click())

        verify(exactly = 4) {
            spySynth.setAmpEnvelope(any())
        }
    }

    @Test
    fun `it displays the amp envelope title`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_title)).check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope attack control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_attack)).check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsAttackSeekBar)).check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope decay control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_decay)).check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsDecaySeekBar)).check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope sustain control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_sustain)).check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsSustainSeekBar)).check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope release control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<MainFragment>()

        onView(withText(R.string.amp_envelope_sustain)).check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsReleaseSeekBar)).check(matches(isDisplayed()))
    }
}