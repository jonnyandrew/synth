package com.flatmapdev.synth.deviceCore.useCase

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetDeviceFeatures @Inject constructor(
    private val deviceFeaturesAdapter: DeviceFeaturesAdapter
) {
    fun execute(): DeviceFeatures {
        return deviceFeaturesAdapter.getDeviceFeatures()
    }
}