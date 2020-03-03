package com.flatmapdev.synth.app.di

import android.content.Context
import com.flatmapdev.synth.aboutUi.AboutFragment
import com.flatmapdev.synth.deviceData.DeviceDataModule
import com.flatmapdev.synth.engineData.EngineDataModule
import com.flatmapdev.synth.jni.JniModule
import com.flatmapdev.synth.mainUi.MainActivity
import com.flatmapdev.synth.mainUi.MainFragment
import com.flatmapdev.synth.oscillatorData.OscillatorDataModule
import com.flatmapdev.synth.shared.scopes.AppScope
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DeviceDataModule::class,
        EngineDataModule::class,
        JniModule::class,
        OscillatorDataModule::class
    ]
)
@AppScope
interface AppComponent {
    fun inject(aboutFragment: AboutFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
