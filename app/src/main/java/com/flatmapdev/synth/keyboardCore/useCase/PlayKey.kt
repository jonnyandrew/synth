package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import dagger.Reusable
import javax.inject.Inject

@Reusable
class PlayKey @Inject constructor(
    private val synthEngineAdapter: SynthEngineAdapter
) {
    fun execute(key: Key) {
        synthEngineAdapter.playNote(key)
    }
}