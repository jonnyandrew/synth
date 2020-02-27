package com.flatmapdev.synth.mainUi

import androidx.fragment.app.testing.launchFragmentInContainer
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
}