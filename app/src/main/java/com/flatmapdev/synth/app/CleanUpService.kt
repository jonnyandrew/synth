package com.flatmapdev.synth.app

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import javax.inject.Inject

class CleanUpService : Service() {
    @Inject
    lateinit var synthEngineAdapter: SynthEngineAdapter

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).appComponent.inject(this)
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)

        synthEngineAdapter.cleanUp()

        stopSelf()
    }
}
