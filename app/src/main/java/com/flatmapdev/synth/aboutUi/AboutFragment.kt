package com.flatmapdev.synth.aboutUi

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.databinding.FragmentAboutBinding
import com.flatmapdev.synth.shared.ui.util.applyTransitions
import com.flatmapdev.synth.shared.ui.util.viewBinding
import javax.inject.Inject

class AboutFragment : Fragment(R.layout.fragment_about) {
    @Inject
    lateinit var viewModelFactory: AboutViewModel.Factory
    private lateinit var viewModel: AboutViewModel
    private val binding by viewBinding(FragmentAboutBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransitions()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AboutViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        viewModel.engineVersion.observe(viewLifecycleOwner, Observer { value ->
            binding.engineVersion.text = getString(
                R.string.app_engine_version, value
            )
        })

        viewModel.deviceFeatures.observe(viewLifecycleOwner, Observer { deviceFeatures ->
            binding.lowLatencyStatus.text = getString(
                R.string.device_features_supports_low_latency,
                deviceFeatures.isLowLatency.toString()
            )
            binding.proLatencyStatus.text = getString(
                R.string.device_features_supports_pro_latency,
                deviceFeatures.isProLatency.toString()
            )
        })
    }
}
