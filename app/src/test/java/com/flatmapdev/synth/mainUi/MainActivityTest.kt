package com.flatmapdev.synth.mainUi

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.device.FakeDeviceDataModule
import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures
import com.flatmapdev.synth.doubles.engine.FakeEngineDataModule
import com.flatmapdev.synth.doubles.engine.adapter.FakeSynthEngineAdapter
import io.mockk.spyk
import io.mockk.verify
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
    fun `it shows the engine version`() {
        testComponentBuilder.fakeEngineDataModule(
            FakeEngineDataModule(
                FakeSynthEngineAdapter(
                    version = "1.2.345"
                )
            )
        )
        getApp().appComponent = testComponentBuilder.build()
        launch<MainActivity>(MainActivity::class.java)

        onView(withId(R.id.engineVersion)).check(matches(withText("Engine version: 1.2.345")))
    }

    @Test
    fun `it shows the low latency support`() {
        getApp().appComponent = testComponentBuilder
            .fakeDeviceDataModule(
                FakeDeviceDataModule(
                    deviceFeaturesAdapter = StubDeviceFeaturesAdapter(
                        deviceFeatures = createDeviceFeatures(
                            isLowLatency = true
                        )
                    )
                )
            ).build()
        launch<MainActivity>(MainActivity::class.java)

        onView(withId(R.id.lowLatencyStatus)).check(matches(withText("Supports low latency: true")))
    }

    @Test
    fun `it shows the pro latency support`() {
        getApp().appComponent = testComponentBuilder
            .fakeDeviceDataModule(
                FakeDeviceDataModule(
                    deviceFeaturesAdapter = StubDeviceFeaturesAdapter(
                        deviceFeatures = createDeviceFeatures(
                            isProLatency = true
                        )
                    )
                )
            ).build()
        launch<MainActivity>(MainActivity::class.java)

        onView(withId(R.id.proLatencyStatus)).check(matches(withText("Supports pro latency: true")))
    }

    @Test
    fun `it shows the keyboard`() {
        getApp().appComponent = testComponentBuilder.build()
        launch<MainActivity>(MainActivity::class.java)

        onView(withId(R.id.keyboard))
            .check(matches(isDisplayed()))
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
        launch<MainActivity>(MainActivity::class.java)

        onView(withId(R.id.keyboard))
            .perform(click())

        verify { spySynthEngineAdapter.playNote(any()) }
    }
}
