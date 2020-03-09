package com.flatmapdev.synth.keyboardData.adapter

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import com.flatmapdev.synth.utils.test
import kotlinx.coroutines.test.runBlockingTest
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
    fun `it gets a null scale if none is persisted`() = runBlockingTest {
        val subject = createSubject()
        subject.getScale()
            .test(this)
            .assertValues(null)
            .finish()
    }

    @Test
    fun `it persists the scale`() = runBlockingTest {
        val scale = Scale(Note.E, ScaleType.Major)
        val adapter1 = createSubject()
        val adapter2 = createSubject()

        val testCollector = adapter2.getScale()
            .test(this)
        adapter1.storeScale(scale)

        // Store the scale with one adapter and check it is available to the other
        testCollector.assertValues(null, scale, null)
            .finish()
    }

    @Test
    fun `it retrieves data stored in a potentially old format 1`() = runBlockingTest {
        sharedPreferences.edit(commit = true) {
            putString("Note", "C_SHARP_D_FLAT")
            putString("ScaleType", "MINOR_PENTATONIC")
        }
        val scale = Scale(Note.C_SHARP_D_FLAT, ScaleType.MinorPentatonic)
        val subject = createSubject()

        subject.getScale()
            .test(this)
            .assertValues(scale)
            .finish()
    }

    @Test
    fun `getScale retrieves data stored in a potentially old format 2`() = runBlockingTest {
        sharedPreferences.edit(commit = true) {
            putString("Note", "A")
            putString("ScaleType", "MAJOR")
        }
        val scale = Scale(Note.A, ScaleType.Major)
        val subject = createSubject()

        subject.getScale()
            .test(this)
            .assertValues(scale)
            .finish()
    }

    @Test
    fun `getScale returns null scale if some data is missing 1`() = runBlockingTest {
        sharedPreferences.edit(commit = true) {
            putString("ScaleType", "MAJOR")
        }
        val subject = createSubject()

        subject.getScale()
            .test(this)
            .assertValues(null)
            .finish()
    }

    @Test
    fun `it returns null if some data is missing 2`() = runBlockingTest {
        sharedPreferences.edit(commit = true) {
            putString("Note", "A")
        }
        val subject = createSubject()

        subject.getScale()
            .test(this)
            .assertValues(null)
            .finish()
    }

    private fun createSubject(): SharedPreferencesScaleAdapter {
        return SharedPreferencesScaleAdapter(sharedPreferences)
    }

    private fun createSharedPreferences(): SharedPreferences {
        return getApp().getSharedPreferences("test", MODE_PRIVATE)
    }
}
