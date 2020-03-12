package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorData.mapper.toDataModel
import com.flatmapdev.synth.oscillatorData.model.OscillatorData

class FakeSynthOscillator(
    private var oscillator: OscillatorData = createOscillator().toDataModel()
) : SynthOscillator {
    override fun getOscillator(): OscillatorData {
        return oscillator
    }

    override fun setOscillator(oscillator: OscillatorData) {
        this.oscillator = oscillator
    }

    override fun setWaveform(waveform: Int) {
        oscillator = oscillator.copy(waveformType = waveform)
    }

    override fun setPitchOffset(pitchOffset: Int) {
        oscillator = oscillator.copy(pitchOffset = pitchOffset)
    }
}
