package com.flatmapdev.synth.jni

import dagger.Binds
import dagger.Module

@Module
abstract class JniModule {
    @Binds
    abstract fun synth(impl: NativeSynth): Synth
}
