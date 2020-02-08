package com.flatmapdev.synth.engineData

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineData.adapter.NativeSynthEngineAdapter
import dagger.Binds
import dagger.Module

@Module
abstract class EngineDataModule {
    @Binds
    abstract fun synthEngineAdapter(impl: NativeSynthEngineAdapter): SynthEngineAdapter

}