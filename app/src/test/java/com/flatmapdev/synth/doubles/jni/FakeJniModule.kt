package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.jni.Synth
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorKey
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class FakeJniModule(
    private val synth: Synth = FakeSynth(),
    private val synthOscillator1: SynthOscillator = FakeSynthOscillator(),
    private val synthOscillator2: SynthOscillator = FakeSynthOscillator()
) {
    @Provides
    fun synth(): Synth = synth

    @Provides
    @IntoMap
    @OscillatorKey(OscillatorId.Osc1)
    fun synthOscillator1(): SynthOscillator = synthOscillator1

    @Provides
    @IntoMap
    @OscillatorKey(OscillatorId.Osc2)
    fun synthOscillator2(): SynthOscillator = synthOscillator2
}
