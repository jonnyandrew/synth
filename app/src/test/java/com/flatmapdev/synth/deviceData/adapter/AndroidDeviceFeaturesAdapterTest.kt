package com.flatmapdev.synth.deviceData.adapter

import android.app.Application
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
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

        assertThat(result.isLowLatency).isTrue()
    }

    @Test
    fun `when device doesn't have low latency result isLowLatency is false`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_LOW_LATENCY,
            false
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertThat(result.isLowLatency).isFalse()
    }

    @Test
    fun `when device has pro latency result isProLatency is true`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_PRO,
            true
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertThat(result.isProLatency).isTrue()
    }

    @Test
    fun `when device doesn't have pro latency result isProLatency is false`() {
        shadowOf(app.packageManager).setSystemFeature(
            PackageManager.FEATURE_AUDIO_PRO,
            false
        )
        val subject = AndroidDeviceFeaturesAdapter(app)

        val result = subject.getDeviceFeatures()

        assertThat(result.isProLatency).isFalse()
    }
}
