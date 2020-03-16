package com.flatmapdev.synth.filterCore.useCase

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.filterCore.model.Filter
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetFilter @Inject constructor(
    private val filterAdapter: FilterAdapter
) {
    fun execute(): Filter {
        return filterAdapter.getFilter()
    }
}
