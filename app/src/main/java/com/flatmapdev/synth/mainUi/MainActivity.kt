package com.flatmapdev.synth.mainUi

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        for (key in keys) {
            val key = (layoutInflater.inflate(R.layout.view_key, keyboard, false) as TextView)
                .apply {
                    tag = key
                    text = key.note.name
                    setOnTouchListener { v, event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> playKey.execute(key)
                            MotionEvent.ACTION_UP -> stopKey.execute(key)
                            else -> return@setOnTouchListener super.onTouchEvent(event)
                        }
                        true
                    }
                }
            keyboard.addView(key)
        }
    }
}
