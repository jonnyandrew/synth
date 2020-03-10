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
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.getProgressFromMiddle
import com.flatmapdev.synth.shared.ui.util.setProgressFromMiddle
import kotlinx.android.synthetic.main.fragment_oscillator.*
import javax.inject.Inject

class OscillatorFragment : Fragment(R.layout.fragment_oscillator) {
    @Inject
    lateinit var oscillatorViewModelFactory: OscillatorViewModel.Factory

    private lateinit var oscillatorViewModel: OscillatorViewModel

    val args: OscillatorFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        oscillatorViewModel = ViewModelProvider(this, oscillatorViewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(toolbar, findNavController())
        toolbar.title = getString(R.string.osc_title, args.oscillatorId.number)
        oscillatorViewModel.init(args.oscillatorId)
        oscillatorViewModel.oscillator.observe(viewLifecycleOwner, Observer {
            oscControlsPitchSeekBar.setProgressFromMiddle(it.pitchOffset)
        })

        setUpOscillatorControls()
    }

    private fun setUpOscillatorControls() {
        oscControlsPitchSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    oscillatorViewModel.setOscillator(
                        args.oscillatorId,
                        Oscillator(seekBar.getProgressFromMiddle())
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
