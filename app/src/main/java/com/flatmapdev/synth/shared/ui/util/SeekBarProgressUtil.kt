package com.flatmapdev.synth.shared.ui.util

import android.widget.SeekBar

fun SeekBar.getProgressFromMiddle(): Int {
    return progress - max / 2
}

fun SeekBar.setProgressFromMiddle(value: Int) {
    progress = value + max / 2
}
