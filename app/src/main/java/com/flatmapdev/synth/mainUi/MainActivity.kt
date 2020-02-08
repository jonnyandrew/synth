package com.flatmapdev.synth.mainUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flatmapdev.synth.R
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.deviceData.AndroidDeviceFeaturesAdapter
import com.flatmapdev.synth.engineData.SynthEngine
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val synthEngine = SynthEngine()
    private val getDeviceFeatures: GetDeviceFeatures = GetDeviceFeatures(
        AndroidDeviceFeaturesAdapter(this)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        engineVersion.text = getString(
            R.string.app_engine_version,
            synthEngine.getVersion()
        )

        val deviceFeatures = getDeviceFeatures.execute()
        lowLatencyStatus.text = getString(
            R.string.device_features_supports_low_latency,
            deviceFeatures.isLowLatency.toString()
        )
        proLatencyStatus.text = getString(
            R.string.device_features_supports_pro_latency,
            deviceFeatures.isProLatency.toString()
        )
    }

}
