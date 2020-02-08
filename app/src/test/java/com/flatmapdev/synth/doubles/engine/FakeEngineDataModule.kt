package com.flatmapdev.synth.doubles.engine

import com.flatmapdev.synth.doubles.engine.adapter.FakeSynthEngineAdapter
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import dagger.Binds
import dagger.Module

@Module
abstract class FakeEngineDataModule {
    @Binds
    abstract fun synthEngineAdapter(impl: FakeSynthEngineAdapter): SynthEngineAdapter

}