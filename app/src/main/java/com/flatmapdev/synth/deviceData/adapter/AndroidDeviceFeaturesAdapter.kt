package com.flatmapdev.synth.deviceData.adapter

import android.content.Context
import android.content.pm.PackageManager
import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures
import javax.inject.Inject

class AndroidDeviceFeaturesAdapter @Inject constructor(
    private val context: Context
) : DeviceFeaturesAdapter {
    override fun getDeviceFeatures(): DeviceFeatures {
        return DeviceFeatures(
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY),
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_PRO)
        )
    }
}