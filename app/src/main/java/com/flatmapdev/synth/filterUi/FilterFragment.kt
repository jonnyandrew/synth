package com.flatmapdev.synth.filterUi

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.jni.SynthFilter
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment(R.layout.fragment_filter) {
//    @Inject
//    lateinit var filterViewModelFactory: FilterViewModel.Factory
//
//    private lateinit var viewModel: FilterViewModel

    @Inject
    lateinit var nativeSynthFilter: SynthFilter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
//        viewModel = ViewModelProvider(this, filterViewModelFactory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(toolbar, findNavController())
//        viewModel.init(args.filterId)
//        viewModel.filter.observe(viewLifecycleOwner, Observer {
//        })

        setUpControls()
    }

    private fun setUpControls() {
        filterCutoffControl.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    nativeSynthFilter.setCutoff(
                        seekBar.progress.toFloat() / 100
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        filterResonanceControl.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    nativeSynthFilter.setResonance(
                        seekBar.progress.toFloat() / 25
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
