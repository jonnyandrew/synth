package com.flatmapdev.synth.doubles.oscillator.adapter

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.Waveform

class FakeOscillatorAdapter(
    override var oscillator: Oscillator = Oscillator(0, Waveform.Sine)
) : OscillatorAdapter {
    override fun setWaveform(waveform: Waveform) {
        oscillator = oscillator.copy(
            waveform = waveform
        )
    }
}
