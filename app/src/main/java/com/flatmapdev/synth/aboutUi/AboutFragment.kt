package com.flatmapdev.synth.aboutUi

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import kotlinx.android.synthetic.main.fragment_about.*
import javax.inject.Inject

class AboutFragment : Fragment(R.layout.fragment_about) {
    @Inject
    lateinit var getDeviceFeatures: GetDeviceFeatures

    @Inject
    lateinit var synthEngineAdapter: SynthEngineAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engineVersion.text = getString(
            R.string.app_engine_version,
            synthEngineAdapter.getVersion()
        )

        val deviceFeatures = getDeviceFeatures.execute()
        lowLatencyStatus.text = getString( R.string.device_features_supports_low_latency, deviceFeatures.isLowLatency.toString() )
        proLatencyStatus.text = getString(
            R.string.device_features_supports_pro_latency,
            deviceFeatures.isProLatency.toString()
        )
    }
}