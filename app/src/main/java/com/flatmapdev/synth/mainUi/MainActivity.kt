package com.flatmapdev.synth.mainUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.deviceData.adapter.AndroidDeviceFeaturesAdapter
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var synthEngineAdapter: SynthEngineAdapter

    @Inject
    lateinit var getDeviceFeatures: GetDeviceFeatures


    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        engineVersion.text = getString(
            R.string.app_engine_version,
            synthEngineAdapter.getVersion()
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
        synthEngineAdapter.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                synthEngineAdapter.playNote()
            MotionEvent.ACTION_UP ->
                synthEngineAdapter.stopNote()
        }

        return super.onTouchEvent(event)
    }
}
