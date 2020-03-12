package com.flatmapdev.synth.oscillatorCore.useCase

import com.flatmapdev.synth.oscillatorCore.adapter.OscillatorAdapter
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SetOscillator @Inject constructor(
    private val oscillatorAdapters: Map<OscillatorId, @JvmSuppressWildcards OscillatorAdapter>
) {
    fun setOscillator(oscillatorId: OscillatorId, oscillator: Oscillator) {
        getAdapter(oscillatorId).oscillator = oscillator
    }

    fun setWaveform(oscillatorId: OscillatorId, waveform: Waveform) {
        getAdapter(oscillatorId).setWaveform(waveform)
    }

    fun setPitchOffset(oscillatorId: OscillatorId, pitchOffset: Int) {
        getAdapter(oscillatorId).setPitchOffset(pitchOffset)
    }

    private fun getAdapter(oscillatorId: OscillatorId) =
        oscillatorAdapters[oscillatorId]
            ?: error("Adapter not bound for ${oscillatorId.name}")
}
