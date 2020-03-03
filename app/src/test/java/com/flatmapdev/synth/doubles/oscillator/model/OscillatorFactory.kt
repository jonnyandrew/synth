package com.flatmapdev.synth.doubles.oscillator.model

import com.flatmapdev.synth.oscillatorCore.model.Oscillator

fun createOscillator(
    pitchOffset: Int = 0
): Oscillator {
    return Oscillator(
        pitchOffset
    )
}
