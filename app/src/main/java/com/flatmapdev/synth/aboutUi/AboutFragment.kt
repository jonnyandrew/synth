package com.flatmapdev.synth.aboutUi

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment(R.layout.fragment_about) {
    @Inject
    lateinit var viewModelFactory: AboutViewModel.Factory
    private lateinit var viewModel: AboutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade(Fade.MODE_IN)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AboutViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.engineVersion.observe(viewLifecycleOwner, Observer { value ->
            engineVersion.text = getString(
                R.string.app_engine_version, value
            )
        })

        viewModel.deviceFeatures.observe(viewLifecycleOwner, Observer { deviceFeatures ->
            lowLatencyStatus.text = getString(
                R.string.device_features_supports_low_latency,
                deviceFeatures.isLowLatency.toString()
            )
            proLatencyStatus.text = getString(
                R.string.device_features_supports_pro_latency,
                deviceFeatures.isProLatency.toString()
            )
        })
    }
}
