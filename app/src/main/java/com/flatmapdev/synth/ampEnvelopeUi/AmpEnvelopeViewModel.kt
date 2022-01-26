package com.flatmapdev.synth.ampEnvelopeUi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.engineCore.useCase.GetAmpEnvelope
import com.flatmapdev.synth.engineCore.useCase.SetAmpEnvelope
import dagger.Reusable
import javax.inject.Inject

class AmpEnvelopeViewModel(
    private val getAmpEnvelope: GetAmpEnvelope,
    private val setAmpEnvelope: SetAmpEnvelope
) : ViewModel() {
    private val _ampEnvelope = MutableLiveData<Envelope>()
    val ampEnvelope: LiveData<Envelope> = _ampEnvelope

    fun init() {
        _ampEnvelope.value = getAmpEnvelope.execute()
    }

    fun setAmpEnvelope(envelope: Envelope) {
        setAmpEnvelope.execute(envelope)
    }

    @Reusable
    class Factory @Inject constructor(
        private val getAmpEnvelope: GetAmpEnvelope,
        private val setAmpEnvelope: SetAmpEnvelope
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AmpEnvelopeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                AmpEnvelopeViewModel(
                    getAmpEnvelope,
                    setAmpEnvelope
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
