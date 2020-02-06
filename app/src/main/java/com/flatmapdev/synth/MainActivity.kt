package com.flatmapdev.synth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flatmapdev.synth.deviceCore.useCase.GetDeviceFeatures
import com.flatmapdev.synth.deviceData.AndroidDeviceFeaturesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()

        val deviceFeatures = GetDeviceFeatures(
            AndroidDeviceFeaturesAdapter(this)
        ).execute()
        sample_text.text = """Has low latency: ${deviceFeatures.isLowLatency}
            |Has pro latency ${deviceFeatures.isProLatency}""".trimMargin()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
