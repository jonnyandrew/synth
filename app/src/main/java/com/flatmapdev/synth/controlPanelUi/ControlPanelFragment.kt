package com.flatmapdev.synth.controlPanelUi

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_control_panel.*

class ControlPanelFragment : Fragment(R.layout.fragment_control_panel) {
    @Inject
    lateinit var viewModelFactory: ControlPanelViewModel.Factory

    private lateinit var viewModel: ControlPanelViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ampEnvelopeControls.setOnClickListener { navigateToAmpEnvelope() }

        osc1Title.text = getString(R.string.osc_title, OscillatorId.Osc1.number)
        osc1Controls.setOnClickListener { navigateToOscillator1() }

        osc2Title.text = getString(R.string.osc_title, OscillatorId.Osc2.number)
        osc2Controls.setOnClickListener { navigateToOscillator2() }
    }

    private fun navigateToAmpEnvelope() {
        findNavController().navigate(
            ControlPanelFragmentDirections
                .actionControlPanelFragmentToAmpEnvelopeFragment()
        )
    }

    private fun navigateToOscillator1() {
        findNavController().navigate(
            ControlPanelFragmentDirections.actionControlPanelFragmentToOscillatorFragment(
                OscillatorId.Osc1
            )
        )
    }

    private fun navigateToOscillator2() {
        findNavController().navigate(
            ControlPanelFragmentDirections.actionControlPanelFragmentToOscillatorFragment(
                OscillatorId.Osc2
            )
        )
    }
}
