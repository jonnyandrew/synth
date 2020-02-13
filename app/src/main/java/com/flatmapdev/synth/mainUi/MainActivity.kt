package com.flatmapdev.synth.mainUi

import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import com.flatmapdev.synth.keyboardCore.model.Key
import com.flatmapdev.synth.keyboardCore.useCase.GetKeyboard
import com.flatmapdev.synth.keyboardCore.useCase.PlayKey
import com.flatmapdev.synth.keyboardCore.useCase.StopKey
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var getDeviceFeatures: GetDeviceFeatures

    @Inject
    lateinit var getKeyboard: GetKeyboard

    @Inject
    lateinit var playKey: PlayKey

    @Inject
    lateinit var stopKey: StopKey

    @Inject
    lateinit var synthEngineAdapter: SynthEngineAdapter


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

        val keys = getKeyboard.execute()
        setupKeyboard(keys)
    }

    private fun setupKeyboard(keys: List<Key>) {
        keyboard.numKeys = keys.size
        keyboard.keyTouchListener = { keyIndex ->
            when(keyIndex) {
                // TODO fix arbitrary stop keys argument
                null -> stopKey.execute(keys[0])
                else -> playKey.execute(keys[keyIndex])
            }
        }
    }
}
