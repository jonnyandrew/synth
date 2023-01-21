package com.flatmapdev.synth.controlPanelUi

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.databinding.FragmentControlPanelBinding
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.viewBinding
import javax.inject.Inject

class ControlPanelFragment : Fragment(R.layout.fragment_control_panel) {
    @Inject
    lateinit var viewModelFactory: ControlPanelViewModel.Factory

    private lateinit var viewModel: ControlPanelViewModel
    private val binding by viewBinding(FragmentControlPanelBinding::bind)

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
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)

        binding.ampEnvelopeTitle.setOnClickListener { navigateToAmpEnvelope() }

        binding.osc1Title.text = getString(R.string.osc_title, OscillatorId.Osc1.number)
        binding.osc1Title.setOnClickListener { navigateToOscillator1() }

        binding.osc2Title.text = getString(R.string.osc_title, OscillatorId.Osc2.number)
        binding.osc2Title.setOnClickListener { navigateToOscillator2() }

        binding.filterTitle.setOnClickListener { navigateToFilter() }
        binding.keyboardTitle.setOnClickListener { navigateToKeyboard() }
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

    private fun navigateToFilter() {
        findNavController().navigate(
            ControlPanelFragmentDirections.actionControlPanelFragmentToFilterFragment()
        )
    }

    private fun navigateToKeyboard() {
        findNavController().navigate(
            ControlPanelFragmentDirections.actionControlPanelFragmentToKeyboardFragment()
        )
    }
}
