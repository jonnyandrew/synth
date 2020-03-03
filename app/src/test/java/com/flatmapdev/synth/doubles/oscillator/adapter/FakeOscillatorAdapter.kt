package com.flatmapdev.synth.doubles.oscillator.adapter

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator

class FakeOscillatorAdapter(
    override var oscillator: Oscillator = Oscillator(0)
) : OscillatorAdapter
