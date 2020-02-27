package com.flatmapdev.synth.aboutUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flatmapdev.synth.deviceCore.model.DeviceFeatures
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import dagger.Reusable
import javax.inject.Inject

class AboutViewModel(
    getDeviceFeatures: GetDeviceFeatures,
    synthEngineAdapter: SynthEngineAdapter
) : ViewModel() {

    private val _deviceFeatures = MutableLiveData<DeviceFeatures>()
    val deviceFeatures: LiveData<DeviceFeatures> = _deviceFeatures

    private val _engineVersion = MutableLiveData<String>()
    val engineVersion: LiveData<String> = _engineVersion

    init {
        _deviceFeatures.value = getDeviceFeatures.execute()
        _engineVersion.value = synthEngineAdapter.version
    }

    @Reusable
    class Factory @Inject constructor(
        private val getDeviceFeatures: GetDeviceFeatures,
        private val synthEngineAdapter: SynthEngineAdapter
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                AboutViewModel(
                    getDeviceFeatures,
                    synthEngineAdapter
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
