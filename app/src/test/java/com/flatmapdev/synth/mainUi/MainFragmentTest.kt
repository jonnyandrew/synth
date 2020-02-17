package com.flatmapdev.synth.mainUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.engine.FakeEngineDataModule
import com.flatmapdev.synth.doubles.engine.adapter.FakeSynthEngineAdapter
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

        Espresso.onView(ViewMatchers.withId(R.id.keyboard))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `when a key is tapped, it sends a signal to the synth engine`() {
        val spySynthEngineAdapter = spyk(FakeSynthEngineAdapter())
        getApp().appComponent = testComponentBuilder
            .fakeEngineDataModule(
                FakeEngineDataModule(
                    synthEngineAdapter = spySynthEngineAdapter
                )
            )
            .build()
        launchFragmentInContainer<MainFragment>()

        Espresso.onView(ViewMatchers.withId(R.id.keyboard))
            .perform(ViewActions.click())

        verify { spySynthEngineAdapter.playNote(any()) }
    }
}