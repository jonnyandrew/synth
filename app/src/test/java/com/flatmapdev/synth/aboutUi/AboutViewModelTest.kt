package com.flatmapdev.synth.aboutUi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures
import com.flatmapdev.synth.doubles.engine.adapter.StubSynthEngineAdapter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AboutViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `it emits the engine version`() {
        val version = "1.26.4-alpha4"
        val subject = AboutViewModel(
            GetDeviceFeatures(StubDeviceFeaturesAdapter()),
            StubSynthEngineAdapter(version = version)
        )

        assertThat(subject.engineVersion.value)
            .isEqualTo(version)
    }

    @Test
    fun `it emits the device features`() {
        val deviceFeatures = createDeviceFeatures(
            isLowLatency = true,
            isProLatency = false
        )
        val subject = AboutViewModel(
            GetDeviceFeatures(
                StubDeviceFeaturesAdapter(
                    deviceFeatures = deviceFeatures
                )
            ),
            StubSynthEngineAdapter()
        )

        assertThat(subject.deviceFeatures.value)
            .isEqualTo(deviceFeatures)
    }
}
