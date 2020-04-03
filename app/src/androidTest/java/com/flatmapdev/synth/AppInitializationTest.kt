package com.flatmapdev.synth

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.flatmapdev.synth.mainUi.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AppInitializationTest {
    /**
     * Test that the app starts without crashing. This verifies that the
     * JNI interface that gets registered by the native library matches the
     * Kotlin interface.
     */
    @Test
    fun testTheAppInitializesWithoutCrashing() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}
