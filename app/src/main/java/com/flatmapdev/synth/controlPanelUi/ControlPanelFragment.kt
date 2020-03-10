package com.flatmapdev.synth.controlPanelUi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.oscillatorCore.model.OscillatorId
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.synthUi.SynthFragmentDirections
import kotlinx.android.synthetic.main.fragment_control_panel.*
import javax.inject.Inject

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(toolbar, findNavController())

        ampEnvelopeTitle.setOnClickListener { navigateToAmpEnvelope() }

        osc1Title.text = getString(R.string.osc_title, OscillatorId.Osc1.number)
        osc1Title.setOnClickListener { navigateToOscillator1() }

        osc2Title.text = getString(R.string.osc_title, OscillatorId.Osc2.number)
        osc2Title.setOnClickListener { navigateToOscillator2() }

        keyboardTitle.setOnClickListener { navigateToKeyboard() }
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

    private fun navigateToKeyboard() {
        findNavController().navigate(
            ControlPanelFragmentDirections.actionControlPanelFragmentToKeyboardFragment()
        )
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
        parentFragment?.findNavController()?.navigate(
            SynthFragmentDirections.actionSynthFragmentToAboutFragment()
        )
    }
}
