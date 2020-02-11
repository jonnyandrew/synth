package com.flatmapdev.synth.mainUi

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as App
        val testComponent = DaggerTestAppComponent.builder().build()
        app.appComponent = testComponent
        scenario = launch<MainActivity>(MainActivity::class.java)
    }

    @Test
    fun `it shows the engine version`() {
        onView(withId(R.id.engineVersion)).check(matches(withText(containsString("Engine version: 1.0.0"))))
    }

    @Test
    fun `it shows the low latency support`() {
        onView(withId(R.id.lowLatencyStatus)).check(matches(withText(containsString("Supports low latency: "))))
    }

    @Test
    fun `it shows the pro latency support`() {
        onView(withId(R.id.proLatencyStatus)).check(matches(withText(containsString("Supports pro latency: "))))
    }
}
