package com.flatmapdev.synth.oscillatorData.adapter

import com.flatmapdev.synth.jni.Pointer
import com.flatmapdev.synth.jni.SynthOscillator
import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import com.flatmapdev.synth.oscillatorData.mapper.toDataModel
import com.flatmapdev.synth.oscillatorData.mapper.toDomainModel

class DefaultOscillatorAdapter(
    private val synth: Pointer,
    private val synthOscillator: SynthOscillator
) : OscillatorAdapter {
    override var oscillator: Oscillator
        get() = synthOscillator.getOscillator(synth).toDomainModel()
        set(value) = synthOscillator.setOscillator(synth, value.toDataModel())

    override fun setWaveform(waveform: Waveform) {
        synthOscillator.setWaveform(synth, waveform.toDataModel())
    }

    override fun setPitchOffset(pitchOffset: Int) {
        synthOscillator.setPitchOffset(synth, pitchOffset)
    }
}
