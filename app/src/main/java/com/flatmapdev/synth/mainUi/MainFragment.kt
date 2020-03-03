package com.flatmapdev.synth.mainUi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.engineCore.model.Envelope
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.oscillatorCore.model.Oscillator
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_amp_envelope.*

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject
    lateinit var mainViewModelFactory: MainViewModel.Factory
    @Inject
    lateinit var ampEnvelopeViewModelFactory: AmpEnvelopeViewModel.Factory
    @Inject
    lateinit var oscillatorViewModelFactory: OscillatorViewModel.Factory

    private lateinit var mainViewModel: MainViewModel
    private lateinit var ampEnvelopeViewModel: AmpEnvelopeViewModel
    private lateinit var oscillatorViewModel: OscillatorViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get()
        ampEnvelopeViewModel = ViewModelProvider(this, ampEnvelopeViewModelFactory).get()
        oscillatorViewModel = ViewModelProvider(this, oscillatorViewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        exitTransition = Fade(Fade.MODE_OUT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.init()
        ampEnvelopeViewModel.init()
        oscillatorViewModel.init()
        mainViewModel.keyboard.observe(viewLifecycleOwner, Observer { keys ->
            setUpKeyboard(keys)
        })

        ampEnvelopeViewModel.ampEnvelope.observe(viewLifecycleOwner, Observer { envelope ->
            setAmpEnvelopeValues(envelope)
        })

        oscillatorViewModel.oscillator1.observe(viewLifecycleOwner, Observer {
            // TODO
        })

        oscillatorViewModel.oscillator2.observe(viewLifecycleOwner, Observer {
            // TODO
        })

        oscillatorViewModel.setOscillator1(Oscillator(12))
        oscillatorViewModel.setOscillator2(Oscillator(24))
        setupAmpEnvelopeControls()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                navigateToAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAbout() {
        findNavController().navigate(R.id.aboutFragment)
    }

    private fun setUpKeyboard(keys: List<Key>) {
        keyboard.numKeys = keys.size
        keyboard.keyTouchListener = { keyIndex ->
            when (keyIndex) {
                null -> mainViewModel.stopKeys()
                else -> mainViewModel.playKey(keys[keyIndex])
            }
        }
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
