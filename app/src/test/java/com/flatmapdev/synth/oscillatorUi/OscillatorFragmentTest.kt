package com.flatmapdev.synth.oscillatorUi

import android.widget.AutoCompleteTextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynthOscillator
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import com.flatmapdev.synth.utils.NavControllerFragmentFactory
import com.flatmapdev.synth.utils.launchAndSetUpFragment
import com.flatmapdev.synth.utils.skipTextInputLayoutAnimations
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OscillatorFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder
    private lateinit var spySynthOscillator: SynthOscillator

    @Before
    fun setUp() {
        spySynthOscillator = spyk(FakeSynthOscillator())
        testComponentBuilder = DaggerTestAppComponent.builder()
            .fakeJniModule(
                FakeJniModule(synthOscillator1 = spySynthOscillator)
            )
    }

    @Test
    fun `it displays the oscillator title`() {
        val expectedString = getApp().getString(R.string.osc_title, 1)

        launch()

        onView(withText(expectedString))
            .check(matches(isDisplayed()))
        onView(withId(R.id.oscControlsPitchSeekBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when the oscillator pitch is changed, it sends the new oscillator config to the synth engine`() {
        launch()

        onView(withId(R.id.oscControlsPitchSeekBar))
            .perform(swipeRight())

        verify(atLeast = 1) {
            spySynthOscillator.setOscillator(any())
        }
    }

    @Test
    fun `when the oscillator pitch is changed, it doesn't update the wrong oscillator in the engine`() {
        val wrongSynthOscillator = spyk(FakeSynthOscillator())
        testComponentBuilder = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(
                    synthOscillator1 = spySynthOscillator,
                    synthOscillator2 = wrongSynthOscillator
                )
            )
        launch()

        onView(withId(R.id.oscControlsPitchSeekBar))
            .perform(swipeRight())

        verify(inverse = true) {
            wrongSynthOscillator.setOscillator(any())
        }
    }

    @Test
    fun `when the oscillator waveform is changed, it sends the new waveform to the synth engine`() {
        val scenario = launch()
        scenario.onFragment { fragment ->
            onView(withId(R.id.waveformLayout)).perform(ViewActions.click())
            onView(withId(R.id.waveformLayout)).perform(skipTextInputLayoutAnimations())
            Assertions.assertThat(fragment.view?.findViewById<AutoCompleteTextView>(R.id.waveform)?.isPopupShowing)
                .isTrue()
            onData(CoreMatchers.equalTo(getApp().getString(R.string.waveform_triangle)))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(ViewActions.click())
        }

        verify {
            spySynthOscillator.setWaveform(Waveform.Triangle.ordinal)
        }
    }

    private fun launch(): FragmentScenario<OscillatorFragment> {
        val fragmentFactory = NavControllerFragmentFactory(
            R.navigation.synth_nav_graph,
            R.id.oscillatorFragment
        )

        getApp().appComponent = testComponentBuilder.build()
        return launchAndSetUpFragment(
            OscillatorFragmentArgs(OscillatorId.Osc1).toBundle(),
            fragmentFactory = fragmentFactory
        )
    }
}
