package com.flatmapdev.synth.jni

import androidx.annotation.Keep
import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
@Keep
class NativeSynthFilter @Inject constructor() : SynthFilter {
    external override fun getFilter(synth: Pointer): FilterData
    external override fun setIsActive(synth: Pointer, isActive: Boolean)
    external override fun setCutoff(synth: Pointer, cutoff: Float)
    external override fun setResonance(synth: Pointer, resonance: Float)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
