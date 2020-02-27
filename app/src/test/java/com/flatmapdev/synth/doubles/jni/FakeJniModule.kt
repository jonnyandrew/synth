package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.Synth
import dagger.Module
import dagger.Provides

@Module
class FakeJniModule(
    private val synth: FakeSynth = FakeSynth()
) {
    @Provides
    fun synth(): Synth = synth
}