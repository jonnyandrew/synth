package com.flatmapdev.synth.app.di

import android.content.Context
import com.flatmapdev.synth.aboutUi.AboutFragment
import com.flatmapdev.synth.ampEnvelopeUi.AmpEnvelopeFragment
import com.flatmapdev.synth.controlPanelUi.ControlPanelFragment
import com.flatmapdev.synth.deviceData.DeviceDataModule
import com.flatmapdev.synth.engineData.EngineDataModule
import com.flatmapdev.synth.jni.JniModule
import com.flatmapdev.synth.keyboardData.KeyboardDataModule
import com.flatmapdev.synth.keyboardUi.KeyboardFragment
import com.flatmapdev.synth.mainUi.MainActivity
import com.flatmapdev.synth.oscillatorData.OscillatorDataModule
import com.flatmapdev.synth.oscillatorUi.OscillatorFragment
import com.flatmapdev.synth.shared.scopes.AppScope
import com.flatmapdev.synth.synthUi.SynthFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DeviceDataModule::class,
        EngineDataModule::class,
        JniModule::class,
        KeyboardDataModule::class,
        OscillatorDataModule::class
    ]
)
@AppScope
interface AppComponent {
    fun inject(aboutFragment: AboutFragment)
    fun inject(ampEnvelopeFragment: AmpEnvelopeFragment)
    fun inject(controlPanelFragment: ControlPanelFragment)
    fun inject(keyboardFragment: KeyboardFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(oscillatorFragment: OscillatorFragment)
    fun inject(synthFragment: SynthFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
