package com.flatmapdev.synth.app.di

import com.flatmapdev.synth.doubles.engine.FakeEngineDataModule
import com.flatmapdev.synth.shared.scopes.AppScope
import dagger.Component

@Component(
    modules = [
        FakeEngineDataModule::class
    ]
)
@AppScope
interface TestAppComponent : AppComponent
