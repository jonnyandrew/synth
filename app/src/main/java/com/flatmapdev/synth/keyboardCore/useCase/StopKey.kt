package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StopKey @Inject constructor(
    private val synthEngineAdapter: SynthEngineAdapter
) {
    fun execute(key: Key) {
        synthEngineAdapter.stopNote(key)
    }
}