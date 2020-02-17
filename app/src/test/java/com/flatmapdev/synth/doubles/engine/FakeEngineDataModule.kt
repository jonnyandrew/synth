package com.flatmapdev.synth.doubles.engine

import com.flatmapdev.synth.doubles.engine.adapter.FakeSynthEngineAdapter
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class FakeEngineDataModule(
    private val synthEngineAdapter: FakeSynthEngineAdapter = FakeSynthEngineAdapter()
) {
    @Provides
    fun synthEngineAdapter(): SynthEngineAdapter {
        return synthEngineAdapter
    }
}