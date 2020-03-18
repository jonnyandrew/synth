package com.flatmapdev.synth.oscillatorData.model

import androidx.annotation.Keep

@Keep
data class OscillatorData(
    val pitchOffset: Int,
    val waveformType: Int
)
