package com.flatmapdev.synth.mainUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import javax.inject.Inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var synthEngineAdapter: SynthEngineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        synthEngineAdapter.start()
    }
}
