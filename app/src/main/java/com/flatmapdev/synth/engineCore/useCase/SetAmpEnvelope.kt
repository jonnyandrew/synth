package com.flatmapdev.synth.engineCore.useCase

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.engineCore.model.Envelope
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SetAmpEnvelope @Inject constructor(
    private val synthEngineAdapter: SynthEngineAdapter
) {
    fun execute(envelope: Envelope) {
        synthEngineAdapter.ampEnvelope = envelope
    }
}