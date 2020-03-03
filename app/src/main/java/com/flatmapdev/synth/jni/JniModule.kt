package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorKey
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class JniModule {
    @Binds
    abstract fun synth(impl: NativeSynth): Synth

    @Binds
    @IntoMap
    @OscillatorKey(OscillatorId.Osc1)
    abstract fun nativeSynthOscillator1(impl: FirstNativeSynthOscillator): SynthOscillator

    @Binds
    @IntoMap
    @OscillatorKey(OscillatorId.Osc2)
    abstract fun nativeSynthOscillator2(impl: SecondNativeSynthOscillator): SynthOscillator
}
