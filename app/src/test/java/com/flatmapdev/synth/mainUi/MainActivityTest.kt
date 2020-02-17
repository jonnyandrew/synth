package com.flatmapdev.synth.mainUi

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.engine.FakeEngineDataModule
import com.flatmapdev.synth.doubles.engine.adapter.FakeSynthEngineAdapter
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `it starts the synth engine`() {
        val spySynthEngineAdapter = spyk(FakeSynthEngineAdapter())
        getApp().appComponent = testComponentBuilder
            .fakeEngineDataModule(
                FakeEngineDataModule(
                    synthEngineAdapter = spySynthEngineAdapter
                )
            )
            .build()
        launch<MainActivity>(MainActivity::class.java)

        onView(isDisplayed())

        verify { spySynthEngineAdapter.start() }
    }

    @Test
    fun `when it is destroyed, it stops the synth engine`() {
        val spySynthEngineAdapter = spyk(FakeSynthEngineAdapter())
        getApp().appComponent = testComponentBuilder
            .fakeEngineDataModule(
                FakeEngineDataModule(
                    synthEngineAdapter = spySynthEngineAdapter
                )
            )
            .build()
        val scenario = launch<MainActivity>(MainActivity::class.java)

        scenario.recreate()

        verifyOrder {
            spySynthEngineAdapter.start()
            spySynthEngineAdapter.stop()
            spySynthEngineAdapter.start()
        }
    }
}
