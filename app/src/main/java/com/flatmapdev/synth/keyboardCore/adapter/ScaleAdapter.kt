package com.flatmapdev.synth.keyboardCore.adapter

import com.flatmapdev.synth.keyboardCore.model.Scale
import kotlinx.coroutines.flow.Flow

interface ScaleAdapter {
    fun getScale(): Flow<Scale?>
    fun storeScale(scale: Scale)
}
