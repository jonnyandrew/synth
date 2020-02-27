package com.flatmapdev.synth.doubles.device.model

import com.flatmapdev.synth.deviceCore.model.DeviceFeatures

fun createDeviceFeatures(
    isLowLatency: Boolean = true,
    isProLatency: Boolean = true
): DeviceFeatures {
    return DeviceFeatures(
        isLowLatency,
        isProLatency
    )
}
