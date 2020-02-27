package com.flatmapdev.synth.deviceData

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceData.adapter.AndroidDeviceFeaturesAdapter
import dagger.Binds
import dagger.Module

@Module
abstract class DeviceDataModule {
    @Binds
    abstract fun deviceFeaturesAdapter(impl: AndroidDeviceFeaturesAdapter): DeviceFeaturesAdapter
}
