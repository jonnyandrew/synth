package com.flatmapdev.synth.oscillatorData.mapper

import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import com.flatmapdev.synth.oscillatorData.model.OscillatorData

fun OscillatorData.toDomainModel(): Oscillator {
    return Oscillator(
        pitchOffset,
        Waveform.fromDataModel(waveformType)
    )
}

fun Oscillator.toDataModel(): OscillatorData {
    return OscillatorData(
        pitchOffset,
        waveform.toDataModel()
    )
}
