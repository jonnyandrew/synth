package com.flatmapdev.synth.synthUi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.databinding.FragmentSynthBinding
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.viewBinding
import javax.inject.Inject

class SynthFragment : Fragment(R.layout.fragment_synth) {
    @Inject
    lateinit var viewModelFactory: SynthViewModel.Factory

    private lateinit var viewModel: SynthViewModel
    private val binding by viewBinding(FragmentSynthBinding::bind)

    private val navController: NavController
        get() {
            val navHostFragment = childFragmentManager.findFragmentById(
                R.id.synthNavHostFragmentContainer
            ) as NavHostFragment
            return navHostFragment.navController
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        viewModel.keyboard.observe(viewLifecycleOwner, Observer { keys ->
            setUpKeyboard(keys)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                return navController.navigateUp()
            }
            R.id.about -> {
                navigateToAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAbout() {
        findNavController().navigate(
            SynthFragmentDirections.actionSynthFragmentToAboutFragment()
        )
    }

    private fun setUpKeyboard(keys: List<Key>) {
        binding.keyboard.numKeys = keys.size
        binding.keyboard.keyTouchListener = { keyIndex ->
            when (keyIndex) {
                null -> viewModel.stopKeys()
                else -> viewModel.playKey(keys[keyIndex])
            }
        }
    }
}
