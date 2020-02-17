package com.flatmapdev.synth.aboutUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.device.FakeDeviceDataModule
import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures
import com.flatmapdev.synth.doubles.engine.FakeEngineDataModule
import com.flatmapdev.synth.doubles.engine.adapter.FakeSynthEngineAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AboutFragmentTest {
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
        launchFragmentInContainer<AboutFragment>()

        Espresso.onView(ViewMatchers.withId(R.id.engineVersion))
            .check(ViewAssertions.matches(ViewMatchers.withText("Engine version: 1.2.345")))
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
        launchFragmentInContainer<AboutFragment>()

        Espresso.onView(ViewMatchers.withId(R.id.lowLatencyStatus))
            .check(ViewAssertions.matches(ViewMatchers.withText("Supports low latency: true")))
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
        launchFragmentInContainer<AboutFragment>()

        Espresso.onView(ViewMatchers.withId(R.id.proLatencyStatus))
            .check(ViewAssertions.matches(ViewMatchers.withText("Supports pro latency: true")))
    }
}