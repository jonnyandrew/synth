package com.flatmapdev.synth.doubles.device.adapter

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures

class StubDeviceFeaturesAdapter(
    private val deviceFeatures: DeviceFeatures
) : DeviceFeaturesAdapter {
    override fun getDeviceFeatures(): DeviceFeatures = deviceFeatures
}