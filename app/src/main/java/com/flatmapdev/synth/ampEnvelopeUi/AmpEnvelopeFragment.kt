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
import com.flatmapdev.synth.databinding.FragmentAmpEnvelopeBinding
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.viewBinding
import javax.inject.Inject

class AmpEnvelopeFragment : Fragment(R.layout.fragment_amp_envelope) {
    @Inject
    lateinit var ampEnvelopeViewModelFactory: AmpEnvelopeViewModel.Factory

    private lateinit var ampEnvelopeViewModel: AmpEnvelopeViewModel
    private val binding by viewBinding(FragmentAmpEnvelopeBinding::bind)

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
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        ampEnvelopeViewModel.init()
        ampEnvelopeViewModel.ampEnvelope.observe(viewLifecycleOwner, Observer { envelope ->
            setAmpEnvelopeValues(envelope)
        })
        setupAmpEnvelopeControls()
    }

    private fun setAmpEnvelopeValues(envelope: Envelope) {
        binding.ampEnvelopeControlsAttackSeekBar.progress = envelope.attackPercent
        binding.ampEnvelopeControlsDecaySeekBar.progress = envelope.decayPercent
        binding.ampEnvelopeControlsSustainSeekBar.progress = envelope.sustainPercent
        binding.ampEnvelopeControlsReleaseSeekBar.progress = envelope.releasePercent
    }

    private fun setupAmpEnvelopeControls() {
        val seekBarChangeListener = object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    ampEnvelopeViewModel.setAmpEnvelope(
                        Envelope(
                            binding.ampEnvelopeControlsAttackSeekBar.progress,
                            binding.ampEnvelopeControlsDecaySeekBar.progress,
                            binding.ampEnvelopeControlsSustainSeekBar.progress,
                            binding.ampEnvelopeControlsReleaseSeekBar.progress
                        )
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
        binding.ampEnvelopeControlsAttackSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.ampEnvelopeControlsDecaySeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.ampEnvelopeControlsSustainSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.ampEnvelopeControlsReleaseSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
    }
}
