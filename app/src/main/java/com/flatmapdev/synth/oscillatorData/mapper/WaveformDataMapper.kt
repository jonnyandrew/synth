package com.flatmapdev.synth.oscillatorData.mapper

import com.flatmapdev.synth.oscillatorCore.model.Waveform

fun Waveform.toDataModel() = this.ordinal

fun Waveform.Companion.fromDataModel(waveform: Int) = Waveform.values()[waveform]
