package com.flatmapdev.synth.controlPanelUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ControlPanelFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
        getApp().appComponent = testComponentBuilder.build()
    }

    @Test
    fun `it displays the amp envelope`() {
        launchFragmentInContainer<ControlPanelFragment>()
        onView(withText(R.string.amp_envelope_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays oscillator 1`() {
        launchFragmentInContainer<ControlPanelFragment>()
        onView(withText(getApp().getString(R.string.osc_title, 1)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays oscillator 2`() {
        launchFragmentInContainer<ControlPanelFragment>()
        onView(withText(getApp().getString(R.string.osc_title, 2)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays keyboard`() {
        launchFragmentInContainer<ControlPanelFragment>()
        onView(withText(R.string.keyboard_title))
            .check(matches(isDisplayed()))
    }
}
