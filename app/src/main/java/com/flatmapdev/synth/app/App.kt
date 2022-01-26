package com.flatmapdev.synth.app

import android.app.Application
import android.content.Intent
import com.flatmapdev.synth.app.di.AppComponent
import com.flatmapdev.synth.app.di.DaggerAppComponent
import android.os.Build

open class App : Application() {
    var appComponent: AppComponent = DaggerAppComponent.factory().create(this)
}
