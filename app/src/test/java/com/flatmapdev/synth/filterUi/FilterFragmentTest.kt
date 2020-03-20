package com.flatmapdev.synth.filterUi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.jni.FakeJniModule
import com.flatmapdev.synth.doubles.jni.FakeSynthFilter
import com.flatmapdev.synth.utils.NavControllerFragmentFactory
import com.flatmapdev.synth.utils.launchAndSetUpFragment
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `when filter cutoff is changed, it sends the new envelope to the synth engine`() {
        val spySynthFilter = spyk(FakeSynthFilter())
        testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthFilter = spySynthFilter)
            )
        launch()

        onView(withId(R.id.filterCutoffControl))
            .perform(swipeRight())

        verify {
            spySynthFilter.setCutoff(any(), any())
        }
    }

    @Test
    fun `when filter resonance is changed, it sends the new envelope to the synth engine`() {
        val spySynthFilter = spyk(FakeSynthFilter())
        testComponentBuilder
            .fakeJniModule(
                FakeJniModule(synthFilter = spySynthFilter)
            )
        launch()

        onView(withId(R.id.filterResonanceControl))
            .perform(swipeRight())

        verify {
            spySynthFilter.setResonance(any(), any())
        }
    }

    @Test
    fun `it displays the filter title`() {
        launch()

        onView(withText(R.string.filter_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the filter cutoff control`() {
        launch()

        onView(withText(R.string.filter_cutoff))
            .check(matches(isDisplayed()))
        onView(withId(R.id.filterCutoffControl))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `it displays the filter resonance control`() {
        launch()

        onView(withText(R.string.filter_resonance))
            .check(matches(isDisplayed()))
        onView(withId(R.id.filterResonanceControl))
            .check(matches(isDisplayed()))
    }

    private fun launch() {
        val fragmentFactory = NavControllerFragmentFactory(
            R.navigation.synth_nav_graph,
            R.id.filterFragment
        )

        getApp().appComponent = testComponentBuilder.build()
        launchAndSetUpFragment<FilterFragment>(
            fragmentFactory = fragmentFactory
        )
    }
}
