package com.flatmapdev.synth.doubles.device.adapter

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures
import com.flatmapdev.synth.doubles.device.model.createDeviceFeatures

class StubDeviceFeaturesAdapter(
    private val deviceFeatures: DeviceFeatures = createDeviceFeatures()
) : DeviceFeaturesAdapter {
    override fun getDeviceFeatures(): DeviceFeatures = deviceFeatures
}
