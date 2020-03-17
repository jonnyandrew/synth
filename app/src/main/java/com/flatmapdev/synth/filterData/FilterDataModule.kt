package com.flatmapdev.synth.filterData

import com.flatmapdev.synth.filterCore.adapter.FilterAdapter
import com.flatmapdev.synth.filterData.adapter.DefaultFilterAdapter
import dagger.Binds
import dagger.Module

@Module
abstract class FilterDataModule {
    @Binds
    abstract fun filterAdapter(impl: DefaultFilterAdapter): FilterAdapter
}
