package com.flatmapdev.synth.oscillatorCore.adapter

import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.Waveform

interface OscillatorAdapter {
    var oscillator: Oscillator

    fun setWaveform(waveform: Waveform)
}
