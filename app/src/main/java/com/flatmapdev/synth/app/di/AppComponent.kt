package com.flatmapdev.synth.app.di

import com.flatmapdev.synth.deviceData.DeviceDataModule
import com.flatmapdev.synth.engineData.EngineDataModule
import com.flatmapdev.synth.jni.JniModule
import com.flatmapdev.synth.mainUi.MainActivity
import com.flatmapdev.synth.shared.scopes.AppScope
import dagger.Component

@Component(
    modules = [
        DeviceDataModule::class,
        EngineDataModule::class,
        JniModule::class
    ]
)
@AppScope
interface AppComponent {
    fun inject(activity: MainActivity)
}
