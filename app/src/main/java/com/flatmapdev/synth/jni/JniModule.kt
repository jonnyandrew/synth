package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorKey
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class JniModule {
    @Binds
    abstract fun synth(impl: NativeSynth): Synth

    @Binds
    abstract fun synthFilter(impl: NativeSynthFilter): SynthFilter

    @Module
    companion object {
        @Provides
        @IntoMap
        @OscillatorKey(OscillatorId.Osc1)
        @JvmStatic
        fun nativeSynthOscillator1(): SynthOscillator {
            return NativeSynthOscillator(
                OscillatorId.Osc1.ordinal
            )
        }

        @Provides
        @IntoMap
        @OscillatorKey(OscillatorId.Osc2)
        @JvmStatic
        fun nativeSynthOscillator2(): SynthOscillator {
            return NativeSynthOscillator(
                OscillatorId.Osc2.ordinal
            )
        }
    }
}
