package com.flatmapdev.synth.ampEnvelopeUi

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_amp_envelope.*

class AmpEnvelopeFragment : Fragment(R.layout.fragment_amp_envelope) {
    @Inject
    lateinit var ampEnvelopeViewModelFactory: AmpEnvelopeViewModel.Factory

    private lateinit var ampEnvelopeViewModel: AmpEnvelopeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        ampEnvelopeViewModel = ViewModelProvider(this, ampEnvelopeViewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(toolbar, findNavController())
        ampEnvelopeViewModel.init()
        ampEnvelopeViewModel.ampEnvelope.observe(viewLifecycleOwner, Observer { envelope ->
            setAmpEnvelopeValues(envelope)
        })
        setupAmpEnvelopeControls()
    }

    private fun setAmpEnvelopeValues(envelope: Envelope) {
        ampEnvelopeControlsAttackSeekBar.progress = envelope.attackPercent
        ampEnvelopeControlsDecaySeekBar.progress = envelope.decayPercent
        ampEnvelopeControlsSustainSeekBar.progress = envelope.sustainPercent
        ampEnvelopeControlsReleaseSeekBar.progress = envelope.releasePercent
    }

    private fun setupAmpEnvelopeControls() {
        val seekBarChangeListener = object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    ampEnvelopeViewModel.setAmpEnvelope(
                        Envelope(
                            ampEnvelopeControlsAttackSeekBar.progress,
                            ampEnvelopeControlsDecaySeekBar.progress,
                            ampEnvelopeControlsSustainSeekBar.progress,
                            ampEnvelopeControlsReleaseSeekBar.progress
                        )
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
        ampEnvelopeControlsAttackSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        ampEnvelopeControlsDecaySeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        ampEnvelopeControlsSustainSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        ampEnvelopeControlsReleaseSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
    }
}
