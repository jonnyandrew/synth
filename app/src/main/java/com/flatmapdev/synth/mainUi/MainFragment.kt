package com.flatmapdev.synth.mainUi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.keyboardCore.useCase.GetKeyboard
import com.flatmapdev.synth.keyboardCore.useCase.PlayKey
import com.flatmapdev.synth.keyboardCore.useCase.StopKeys
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject
    lateinit var getKeyboard: GetKeyboard

    @Inject
    lateinit var playKey: PlayKey

    @Inject
    lateinit var stopKeys: StopKeys

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        exitTransition =  Fade(Fade.MODE_OUT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val keys = getKeyboard.execute()
        keyboard.numKeys = keys.size
        keyboard.keyTouchListener = { keyIndex ->
            when (keyIndex) {
                null -> stopKeys.execute()
                else -> playKey.execute(keys[keyIndex])
            }
        }
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
}