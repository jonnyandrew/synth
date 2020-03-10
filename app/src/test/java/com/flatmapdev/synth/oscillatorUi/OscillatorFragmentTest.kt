package com.flatmapdev.synth.oscillatorUi

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
import com.flatmapdev.synth.utils.NavControllerFragmentFactory
import com.flatmapdev.synth.utils.launchAndSetUpFragment
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
        val spySynthOscillator = spyk(FakeSynthOscillator())
        testComponentBuilder = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthOscillator1 = spySynthOscillator)
            )

        launch()

        onView(withId(R.id.oscControlsPitchSeekBar))
            .perform(swipeRight())

        verify(atLeast = 1) {
            spySynthOscillator.setOscillator(any())
        }
    }

    @Test
    fun `when the oscillator pitch is changed, it doesn't update the wrong oscillator in the engine`() {
        val spySynthOscillator = spyk(FakeSynthOscillator())
        testComponentBuilder = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthOscillator2 = spySynthOscillator)
            )
        launch()

        onView(withId(R.id.oscControlsPitchSeekBar))
            .perform(swipeRight())

        verify(inverse = true) {
            spySynthOscillator.setOscillator(any())
        }
    }

    private fun launch() {
        val fragmentFactory = NavControllerFragmentFactory(
            R.navigation.synth_nav_graph,
            R.id.oscillatorFragment
        )

        getApp().appComponent = testComponentBuilder.build()
        launchAndSetUpFragment<OscillatorFragment>(
            OscillatorFragmentArgs(OscillatorId.Osc1).toBundle(),
            fragmentFactory = fragmentFactory
        )
    }
}
