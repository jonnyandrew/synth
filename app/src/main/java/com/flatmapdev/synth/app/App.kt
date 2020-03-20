package com.flatmapdev.synth.app

import android.app.Application
import android.content.Intent
import com.flatmapdev.synth.app.di.AppComponent
import com.flatmapdev.synth.app.di.DaggerAppComponent

open class App : Application() {
    var appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()

        startService(Intent(baseContext, CleanUpService::class.java))
    }
}
