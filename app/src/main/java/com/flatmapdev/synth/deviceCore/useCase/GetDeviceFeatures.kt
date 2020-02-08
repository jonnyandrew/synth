package com.flatmapdev.synth.deviceCore.useCase

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures
import dagger.Reusable

@Reusable
class GetDeviceFeatures(
    private val deviceFeaturesAdapter: DeviceFeaturesAdapter
) {
    fun execute(): DeviceFeatures {
        return deviceFeaturesAdapter.getDeviceFeatures()
    }
}