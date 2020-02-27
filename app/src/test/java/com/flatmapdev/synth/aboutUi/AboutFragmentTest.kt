package com.flatmapdev.synth.aboutUi

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.device.FakeDeviceDataModule
import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynth
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
        testComponentBuilder.fakeJniModule(
            FakeJniModule(
                synth = FakeSynth(
                    version = "1.2.345"
                )
            )
        )
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<AboutFragment>()

        onView(withId(R.id.engineVersion))
            .check(matches(withText("Engine version: 1.2.345")))
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

        onView(withId(R.id.lowLatencyStatus))
            .check(matches(withText("Supports low latency: true")))
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

        onView(withId(R.id.proLatencyStatus))
            .check(matches(withText("Supports pro latency: true")))
    }
}