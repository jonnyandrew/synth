package com.flatmapdev.synth.jni

import androidx.annotation.Keep
import com.flatmapdev.synth.filterData.model.FilterData
import com.flatmapdev.synth.shared.scopes.AppScope
import javax.inject.Inject

@AppScope
@Keep
class NativeSynthFilter @Inject constructor() : SynthFilter {
    external override fun getFilter(): FilterData
    external override fun setIsActive(isActive: Boolean)
    external override fun setCutoff(cutoff: Float)
    external override fun setResonance(resonance: Float)

    companion object {
        init {
            System.loadLibrary("synth-engine")
        }
    }
}
