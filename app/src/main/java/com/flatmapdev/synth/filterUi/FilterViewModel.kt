package com.flatmapdev.synth.filterUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flatmapdev.synth.filterCore.useCase.GetFilter
import com.flatmapdev.synth.filterCore.useCase.SetFilter
import com.flatmapdev.synth.shared.core.model.Percent
import com.flatmapdev.synth.shared.data.mapper.toFrequency
import com.flatmapdev.synth.shared.data.mapper.toPercent
import dagger.Reusable
import javax.inject.Inject

class FilterViewModel(
    private val getFilter: GetFilter,
    private val setFilter: SetFilter
) : ViewModel() {
    private val _filter = MutableLiveData<FilterControls>()
    val filter: LiveData<FilterControls> = _filter

    fun init() {
        _filter.value = getFilter.execute().let {
            FilterControls(
                it.cutoff.toPercent(),
                it.resonance
            )
        }
    }

    fun setCutoff(cutoff: Percent) {
        setFilter.setCutoff(cutoff.toFrequency())
    }

    fun setResonance(resonance: Percent) {
        setFilter.setResonance(resonance)
    }

    data class FilterControls(
        val cutoff: Percent,
        val resonance: Percent
    )

    @Reusable
    class Factory @Inject constructor(
        private val getFilter: GetFilter,
        private val setFilter: SetFilter
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                FilterViewModel(
                    getFilter,
                    setFilter
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
