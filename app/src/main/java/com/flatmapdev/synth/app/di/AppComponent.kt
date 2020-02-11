package com.flatmapdev.synth.app.di

import android.content.Context
import com.flatmapdev.synth.deviceData.DeviceDataModule
import com.flatmapdev.synth.engineData.EngineDataModule
import com.flatmapdev.synth.mainUi.MainActivity
import com.flatmapdev.synth.shared.scopes.AppScope
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DeviceDataModule::class,
        EngineDataModule::class
    ]
)
@AppScope
interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
