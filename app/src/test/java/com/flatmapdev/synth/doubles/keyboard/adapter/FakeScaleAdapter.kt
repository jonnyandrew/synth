package com.flatmapdev.synth.doubles.keyboard.adapter

import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Scale
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeScaleAdapter(
    scale: Scale? = null
) : ScaleAdapter {
    private val scaleChannel = ConflatedBroadcastChannel(scale)

    override fun getScale(): Flow<Scale?> = scaleChannel.asFlow()

    override fun storeScale(scale: Scale) {
        scaleChannel.offer(scale)
    }
}