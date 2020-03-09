package com.flatmapdev.synth.keyboardUi

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import com.flatmapdev.synth.keyboardShared.toUiString
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.setDropDownItems
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_keyboard.*

class KeyboardFragment : Fragment(R.layout.fragment_keyboard) {
    @Inject
    lateinit var keyboardViewModelFactory: KeyboardViewModel.Factory

    private lateinit var viewModel: KeyboardViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, keyboardViewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        viewModel.scale.observe(viewLifecycleOwner, Observer {
            scaleTonic.setText(getString(it.tonic.toUiString()), false)
            scaleType.setText(getString(it.type.toUiString()), false)
        })

        scaleTonic.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setScaleTonic(Note.values()[position])
            }
        }
        scaleType.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setScaleType(ScaleType.values()[position])
            }
        }
        val noteItems = Note.values()
            .map(Note::toUiString)
            .map(::getString)
        scaleTonic.setDropDownItems(noteItems)

        val scaleTypeItems = ScaleType.values()
            .map(ScaleType::toUiString)
            .map(::getString)
        scaleType.setDropDownItems(scaleTypeItems)
    }
}
