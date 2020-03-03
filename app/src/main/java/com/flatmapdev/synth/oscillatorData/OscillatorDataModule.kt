package com.flatmapdev.synth.oscillatorData

import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorKey
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.oscillatorData.adapter.DefaultOscillatorAdapter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class OscillatorDataModule {
    @Module
    companion object {
        @Provides
        @IntoMap
        @OscillatorKey(OscillatorId.Osc1)
        @JvmStatic
        fun oscillator1DefaultOscillatorAdapter(
            synthOscillators: Map<OscillatorId, @JvmSuppressWildcards SynthOscillator>
        ): OscillatorAdapter {
            return DefaultOscillatorAdapter(
                synthOscillators[OscillatorId.Osc1] ?: error("No oscillator bound")
            )
        }

        @Provides
        @IntoMap
        @OscillatorKey(OscillatorId.Osc2)
        @JvmStatic
        fun oscillator2DefaultOscillatorAdapter(
            synthOscillators: Map<OscillatorId, @JvmSuppressWildcards SynthOscillator>
        ): OscillatorAdapter {
            return DefaultOscillatorAdapter(
                synthOscillators[OscillatorId.Osc2] ?: error("No oscillator bound")
            )
        }
    }
}
