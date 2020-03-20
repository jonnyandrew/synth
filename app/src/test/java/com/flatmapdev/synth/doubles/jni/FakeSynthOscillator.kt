package com.flatmapdev.synth.doubles.jni

import com.flatmapdev.synth.doubles.oscillator.model.createOscillator
import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorData.mapper.toDataModel
import com.flatmapdev.synth.oscillatorData.model.OscillatorData

class FakeSynthOscillator(
    private var oscillator: OscillatorData = createOscillator().toDataModel()
) : SynthOscillator {
    override fun getOscillator(synth: Pointer): OscillatorData {
        return oscillator
    }

    override fun setOscillator(synth: Pointer, oscillator: OscillatorData) {
        this.oscillator = oscillator
    }

    override fun setWaveform(synth: Pointer, waveform: Int) {
        oscillator = oscillator.copy(waveformType = waveform)
    }

    override fun setPitchOffset(synth: Pointer, pitchOffset: Int) {
        oscillator = oscillator.copy(pitchOffset = pitchOffset)
    }
}
