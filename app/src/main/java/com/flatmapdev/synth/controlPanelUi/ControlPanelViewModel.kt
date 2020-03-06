package com.flatmapdev.synth.controlPanelUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Reusable
import javax.inject.Inject

class ControlPanelViewModel() : ViewModel() {
    @Reusable
    class Factory @Inject constructor() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ControlPanelViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                ControlPanelViewModel() as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
