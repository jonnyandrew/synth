package com.flatmapdev.synth.app.di

import com.flatmapdev.synth.doubles.device.FakeDeviceDataModule
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.engineData.EngineDataModule
import com.flatmapdev.synth.oscillatorData.OscillatorDataModule
import com.flatmapdev.synth.shared.scopes.AppScope
import dagger.Component

@Component(
    modules = [
        EngineDataModule::class,
        FakeDeviceDataModule::class,
        FakeJniModule::class,
        OscillatorDataModule::class
    ]
)
@AppScope
interface TestAppComponent : AppComponent
