package com.flatmapdev.synth.deviceCore.useCase

import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetDeviceFeaturesTest {
    @Test
    fun `it returns the device features`() {
        val deviceFeatures = createDeviceFeatures()

        val subject = GetDeviceFeatures(
            StubDeviceFeaturesAdapter(
                deviceFeatures
            )
        )

        val result = subject.execute()

        assertThat(deviceFeatures).isEqualTo(result)
    }
}
