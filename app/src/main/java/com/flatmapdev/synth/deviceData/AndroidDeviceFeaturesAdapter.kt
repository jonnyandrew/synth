package com.flatmapdev.synth.deviceData

import android.content.Context
import android.content.pm.PackageManager
import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures

class AndroidDeviceFeaturesAdapter(
    private val context: Context
) : DeviceFeaturesAdapter {
    override fun getDeviceFeatures(): DeviceFeatures {
        return DeviceFeatures(
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY),
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_PRO)
        )
    }
}