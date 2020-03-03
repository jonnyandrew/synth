package com.flatmapdev.synth.oscillatorCore.adapter

import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import dagger.MapKey

@MapKey
annotation class OscillatorKey(
    val value: OscillatorId
)
