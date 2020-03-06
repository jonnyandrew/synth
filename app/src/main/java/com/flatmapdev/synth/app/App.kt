package com.flatmapdev.synth.app

import android.app.Application
import com.flatmapdev.synth.app.di.AppComponent
import com.flatmapdev.synth.app.di.DaggerAppComponent

open class App : Application() {
    var appComponent: AppComponent = DaggerAppComponent.factory().create(this)
}
