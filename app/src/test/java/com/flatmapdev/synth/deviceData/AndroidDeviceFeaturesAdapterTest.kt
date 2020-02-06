package com.flatmapdev.synth.deviceData

import android.app.Application
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf


@RunWith(AndroidJUnit4::class)
class AndroidDeviceFeaturesAdapterTest {
    lateinit var app: Application

    @Before
    fun setUp() {
        app = getApplicationContext()
    }

    @Test
    fun `when device has low latency result isLowLatency is true`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_LOW_LATENCY,
            true
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertTrue(result.isLowLatency)
    }

    @Test
    fun `when device doesn't have low latency result isLowLatency is false`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_LOW_LATENCY,
            false
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertFalse(result.isLowLatency)
    }

    @Test
    fun `when device has pro latency result isProLatency is true`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_PRO,
            true
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertTrue(result.isProLatency)
    }

    @Test
    fun `when device doesn't have pro latency result isProLatency is false`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_PRO,
            false
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertFalse(result.isProLatency)
    }
}