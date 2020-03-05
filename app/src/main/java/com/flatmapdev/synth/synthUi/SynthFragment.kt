package com.flatmapdev.synth.synthUi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.transition.Fade
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.keyboardCore.model.Key
import kotlinx.android.synthetic.main.fragment_synth.*
import javax.inject.Inject

class SynthFragment : Fragment(R.layout.fragment_synth) {
    @Inject
    lateinit var viewModelFactory: SynthViewModel.Factory

    private lateinit var viewModel: SynthViewModel

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
        exitTransition = Fade(Fade.MODE_OUT)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.let { appCompatActivity ->
            NavigationUI.setupActionBarWithNavController(appCompatActivity, navController)
        }

        /**
         * This statement is a workaround for a bug which occurs while testing this
         * Fragment inside a FragmentScenario (i.e. launchFragmentInContainer<SynthFragment>()).
         * If the NavHost in this fragment has app:defaultNavHost="true" set, then
         * the Fragment crashes in onCreateView with the following exception:
         *
         *     android.view.InflateException
         *     ...
         *     Caused by: IllegalStateException: FragmentManager is already executing transactions
         *     ...
         *     at at androidx.navigation.fragment.NavHostFragment.onAttach
         *
         * It's because NavHostFragment tries to run a similar transaction during it's onAttach.
         * So running the transaction later (here in onActivityCreated) is a workaround for now.
         */
        childFragmentManager.beginTransaction().setPrimaryNavigationFragment(
            childFragmentManager.findFragmentById(R.id.synthNavHostFragmentContainer)
        ).commit()
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
        keyboard.numKeys = keys.size
        keyboard.keyTouchListener = { keyIndex ->
            when (keyIndex) {
                null -> viewModel.stopKeys()
                else -> viewModel.playKey(keys[keyIndex])
            }
        }
    }
}
