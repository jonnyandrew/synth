package com.flatmapdev.synth.mainUi

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynth
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `it starts the synth engine`() {
        val spySynth = spyk(FakeSynth())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(
                    synth = spySynth
                )
            )
            .build()
        launch<MainActivity>(MainActivity::class.java)

        onView(isDisplayed())

        verify { spySynth.start() }
    }

    @Test
    fun `when it is destroyed, it stops the synth engine`() {
        val spySynth = spyk(FakeSynth())
        getApp().appComponent = testComponentBuilder
            .fakeJniModule(
                FakeJniModule(
                    synth = spySynth
                )
            )
            .build()
        val scenario = launch<MainActivity>(MainActivity::class.java)

        scenario.recreate()

        verifyOrder {
            spySynth.start()
            spySynth.stop()
            spySynth.start()
        }
    }
}
