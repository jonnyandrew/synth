package com.flatmapdev.synth.doubles.device

import com.flatmapdev.synth.deviceCore.adapter.DeviceFeaturesAdapter
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures
import com.flatmapdev.synth.doubles.device.adapter.StubDeviceFeaturesAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class FakeDeviceDataModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun deviceFeaturesAdapter(): DeviceFeaturesAdapter {
            return StubDeviceFeaturesAdapter(
                DeviceFeatures(
                    isLowLatency = false,
                    isProLatency = true
                )
            )
        }
    }
}