package com.flatmapdev.synth.keyboardData.adapter

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesScaleAdapterTest {
    private lateinit var sharedPreferences: SharedPreferences
    @Before
    fun setUp() {
        sharedPreferences = createSharedPreferences()
    }

    @Test
    fun `it gets a null scale if none is persisted`() = runTest {
        val subject = createSubject()
        subject.getScale()
            .test {
                assertThat(awaitItem()).isEqualTo(null)
            }
    }

    @Test
    fun `it persists the scale`() = runTest {
        val scale = Scale(Note.E, ScaleType.Major)
        val adapter1 = createSubject()
        val adapter2 = createSubject()

        adapter2.getScale()
            .test {
                assertThat(awaitItem()).isEqualTo(null)
                adapter1.storeScale(scale)
                assertThat(awaitItem()).isEqualTo(scale)
            }
    }

    @Test
    fun `it retrieves data stored in a potentially old format 1`() = runTest {
        sharedPreferences.edit(commit = true) {
            putString("Note", "C_SHARP_D_FLAT")
            putString("ScaleType", "MINOR_PENTATONIC")
        }
        val scale = Scale(Note.C_SHARP_D_FLAT, ScaleType.MinorPentatonic)
        val subject = createSubject()

        subject.getScale().test {
            assertThat(awaitItem()).isEqualTo(scale)
        }
    }

    @Test
    fun `getScale retrieves data stored in a potentially old format 2`() = runTest {
        sharedPreferences.edit(commit = true) {
            putString("Note", "A")
            putString("ScaleType", "MAJOR")
        }
        val scale = Scale(Note.A, ScaleType.Major)
        val subject = createSubject()

        subject.getScale().test {
            assertThat(awaitItem()).isEqualTo(scale)
        }
    }

    @Test
    fun `getScale returns null scale if some data is missing 1`() = runTest {
        sharedPreferences.edit(commit = true) {
            putString("ScaleType", "MAJOR")
        }
        val subject = createSubject()

        subject.getScale()
            .test {
                assertThat(awaitItem()).isNull()
            }
    }

    @Test
    fun `it returns null if some data is missing 2`() = runTest {
        sharedPreferences.edit(commit = true) {
            putString("Note", "A")
        }
        val subject = createSubject()

        subject.getScale()
            .test {
                assertThat(awaitItem()).isNull()
            }
    }

    private fun createSubject(): SharedPreferencesScaleAdapter {
        return SharedPreferencesScaleAdapter(sharedPreferences)
    }

    private fun createSharedPreferences(): SharedPreferences {
        return getApp().getSharedPreferences("test", MODE_PRIVATE)
    }
}
