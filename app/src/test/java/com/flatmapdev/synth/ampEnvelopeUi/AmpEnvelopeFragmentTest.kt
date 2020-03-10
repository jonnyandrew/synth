package com.flatmapdev.synth.ampEnvelopeUi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynth
import com.flatmapdev.synth.utils.NavControllerFragmentFactory
import com.flatmapdev.synth.utils.launchAndSetUpFragment
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AmpEnvelopeFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `when a amp envelope is changed, it sends the new envelope to the synth engine`() {
        val spySynth = spyk(FakeSynth())
        testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synth = spySynth)
            )
        launch()

        onView(withId(R.id.ampEnvelopeControlsAttackSeekBar))
            .perform(click())
        onView(withId(R.id.ampEnvelopeControlsDecaySeekBar))
            .perform(click())
        onView(withId(R.id.ampEnvelopeControlsSustainSeekBar))
            .perform(click())
        onView(withId(R.id.ampEnvelopeControlsReleaseSeekBar))
            .perform(click())

        verify(exactly = 4) {
            spySynth.setAmpEnvelope(any())
        }
    }

    @Test
    fun `it displays the amp envelope title`() {
        launch()

        onView(withText(R.string.amp_envelope_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope attack control`() {
        launch()

        onView(withText(R.string.amp_envelope_attack))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsAttackSeekBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope decay control`() {
        launch()

        onView(withText(R.string.amp_envelope_decay))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsDecaySeekBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope sustain control`() {
        launch()
        onView(withText(R.string.amp_envelope_sustain))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsSustainSeekBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the amp envelope release control`() {
        launch()

        onView(withText(R.string.amp_envelope_sustain))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ampEnvelopeControlsReleaseSeekBar))
            .check(matches(isDisplayed()))
    }

    private fun launch() {
        val fragmentFactory = NavControllerFragmentFactory(
            R.navigation.synth_nav_graph,
            R.id.ampEnvelopeFragment
        )

        getApp().appComponent = testComponentBuilder.build()
        launchAndSetUpFragment<AmpEnvelopeFragment>(
            fragmentFactory = fragmentFactory
        )
    }
}
