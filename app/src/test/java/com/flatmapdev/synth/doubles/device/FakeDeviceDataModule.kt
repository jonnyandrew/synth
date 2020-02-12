package com.flatmapdev.synth.doubles.device

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import dagger.Module
import dagger.Provides


@Module
class FakeDeviceDataModule(
    private val deviceFeaturesAdapter: StubDeviceFeaturesAdapter = StubDeviceFeaturesAdapter()
) {
    @Provides
    fun deviceFeaturesAdapter(): DeviceFeaturesAdapter {
        return deviceFeaturesAdapter
    }
}