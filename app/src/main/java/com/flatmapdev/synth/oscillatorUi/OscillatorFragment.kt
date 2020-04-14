package com.flatmapdev.synth.oscillatorUi

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.databinding.FragmentOscillatorBinding
import com.flatmapdev.synth.oscillatorCore.model.Waveform
import com.flatmapdev.synth.oscillatorShared.toUiString
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.getProgressFromMiddle
import com.flatmapdev.synth.shared.ui.util.setDropDownItems
import com.flatmapdev.synth.shared.ui.util.setProgressFromMiddle
import com.flatmapdev.synth.shared.ui.util.viewBinding
import javax.inject.Inject

class OscillatorFragment : Fragment(R.layout.fragment_oscillator) {
    @Inject
    lateinit var oscillatorViewModelFactory: OscillatorViewModel.Factory

    private lateinit var viewModel: OscillatorViewModel
    private val binding by viewBinding(FragmentOscillatorBinding::bind)

    val args: OscillatorFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, oscillatorViewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        binding.toolbar.title = getString(R.string.osc_title, args.oscillatorId.number)
        viewModel.init(args.oscillatorId)
        viewModel.oscillator.observe(viewLifecycleOwner, Observer {
            binding.oscControlsPitchSeekBar.setProgressFromMiddle(it.pitchOffset)
            binding.waveform.setText(getString(it.waveform.toUiString()), false)
        })

        setUpPitchControls()
        setUpWaveformControls()
    }

    private fun setUpPitchControls() {
        binding.oscControlsPitchSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.setPitchOffset(
                        args.oscillatorId,
                        seekBar.getProgressFromMiddle()
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setUpWaveformControls() {
        val waveforms = Waveform.values()

        val labels = waveforms.map(Waveform::toUiString)
            .map(::getString)
        binding.waveform.apply {
            setDropDownItems(labels)
            setOnItemClickListener { _, _, position, _ ->
                viewModel.setWaveform(
                    args.oscillatorId,
                    waveforms[position]
                )
            }
        }
    }
}
