package com.flatmapdev.synth.jni

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorKey
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.shared.scopes.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class JniModule {
    @Binds
    abstract fun synth(impl: NativeSynthEngine): SynthEngine

    @Binds
    abstract fun synthFilter(impl: NativeSynthFilter): SynthFilter

    @Module
    companion object {
        @Provides
        @JvmStatic
        @AppScope
        fun synthPointer(synthEngine: SynthEngine): Pointer {
            return synthEngine.initialize()
        }

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
