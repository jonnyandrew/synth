package com.flatmapdev.synth.oscillatorShared

import androidx.annotation.StringRes
import com.flatmapdev.synth.R
import com.flatmapdev.synth.oscillatorCore.model.Waveform

@StringRes
fun Waveform.toUiString(): Int = when (this) {
    Waveform.Sine -> R.string.waveform_sine
    Waveform.Square -> R.string.waveform_square
    Waveform.Triangle -> R.string.waveform_triangle
    Waveform.Noise -> R.string.waveform_noise
}
