package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class GetScale @Inject constructor(
    private val scaleAdapter: ScaleAdapter
) {
    fun execute(): Flow<Scale> {
        return scaleAdapter.getScale()
            .map {
                it ?: DEFAULT_SCALE
            }
    }

    companion object {
        val DEFAULT_SCALE = Scale(Note.C, ScaleType.Pentatonic)
    }
}
