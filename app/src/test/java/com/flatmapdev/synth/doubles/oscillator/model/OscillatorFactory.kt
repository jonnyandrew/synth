package com.flatmapdev.synth.doubles.oscillator.model

import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.Waveform

fun createOscillator(
    pitchOffset: Int = 0,
    waveform: Waveform = Waveform.Sine
): Oscillator {
    return Oscillator(
        pitchOffset,
        waveform
    )
}
