package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthEngine
import com.flatmapdev.synth.jni.SynthFilter
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorKey
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class FakeJniModule(
    private val synthEngine: SynthEngine = FakeSynthEngine(),
    private val synthOscillator1: SynthOscillator = FakeSynthOscillator(),
    private val synthOscillator2: SynthOscillator = FakeSynthOscillator(),
    private val synthFilter: SynthFilter = FakeSynthFilter()

) {
    @Provides
    fun synthEngine(): SynthEngine = synthEngine

    @Provides
    fun synth(): Pointer {
        return 0
    }

    @Provides
    @IntoMap
    @OscillatorKey(OscillatorId.Osc1)
    fun synthOscillator1(): SynthOscillator = synthOscillator1

    @Provides
    @IntoMap
    @OscillatorKey(OscillatorId.Osc2)
    fun synthOscillator2(): SynthOscillator = synthOscillator2

    @Provides
    fun synthFilter(): SynthFilter = synthFilter
}
