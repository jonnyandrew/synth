package com.flatmapdev.synth.deviceCore.useCase

import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures
import junit.framework.Assert.assertEquals
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

        assertEquals(deviceFeatures, result)
    }
}