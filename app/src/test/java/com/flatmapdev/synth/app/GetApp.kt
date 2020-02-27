package com.flatmapdev.synth.app

import androidx.test.platform.app.InstrumentationRegistry

fun getApp(): App {
    return InstrumentationRegistry
        .getInstrumentation()
        .targetContext
        .applicationContext
            as App
}
