package com.flatmapdev.synth.keyboardCore.useCase

import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Scale
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SetScale @Inject constructor(
    private val scaleAdapter: ScaleAdapter
) {
    fun execute(scale: Scale) {
        scaleAdapter.storeScale(scale)
    }
}
