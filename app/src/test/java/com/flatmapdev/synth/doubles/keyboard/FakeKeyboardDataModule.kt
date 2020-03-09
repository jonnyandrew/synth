package com.flatmapdev.synth.doubles.keyboard

import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.adapter.ScaleAdapter
import dagger.Module
import dagger.Provides

@Module
class FakeKeyboardDataModule(
    private val scaleAdapter: FakeScaleAdapter = FakeScaleAdapter()
) {
    @Provides
    fun scaleAdapter(): ScaleAdapter {
        return scaleAdapter
    }
}
