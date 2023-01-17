package com.flatmapdev.synth.doubles.keyboard.adapter

import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Scale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeScaleAdapter(
    scale: Scale? = null
) : ScaleAdapter {
    private val scale = MutableStateFlow(scale)

    override fun getScale(): Flow<Scale?> = scale

    override fun storeScale(scale: Scale) {
        this.scale.tryEmit(scale)
    }
}
