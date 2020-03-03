package com.flatmapdev.synth.oscillatorCore.useCase

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SetOscillator @Inject constructor(
    private val oscillatorAdapters: Map<OscillatorId, @JvmSuppressWildcards OscillatorAdapter>
) {
    fun execute(oscillatorId: OscillatorId, oscillator: Oscillator) {
        val adapter =
            oscillatorAdapters[oscillatorId]
                ?: error("Adapter not bound for ${oscillatorId.name}")

        adapter.oscillator = oscillator
    }
}
