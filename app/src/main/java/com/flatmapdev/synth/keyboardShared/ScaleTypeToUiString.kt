package com.flatmapdev.synth.keyboardShared

import androidx.annotation.StringRes
import com.flatmapdev.synth.R
import com.flatmapdev.synth.keyboardCore.model.ScaleType

@StringRes
fun ScaleType.toUiString() = when (this) {
    ScaleType.Major -> R.string.scale_type_major
    ScaleType.Minor -> R.string.scale_type_minor
    ScaleType.Pentatonic -> R.string.scale_type_pentatonic
}