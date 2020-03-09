package com.flatmapdev.synth.keyboardUi

import android.widget.AutoCompleteTextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.di.DaggerTestAppComponent
import com.flatmapdev.synth.app.getApp
import com.flatmapdev.synth.doubles.keyboard.FakeKeyboardDataModule
import com.flatmapdev.synth.doubles.keyboard.adapter.FakeScaleAdapter
import com.flatmapdev.synth.keyboardCore.model.Note
import com.flatmapdev.synth.keyboardCore.model.Scale
import com.flatmapdev.synth.keyboardCore.model.ScaleType
import com.flatmapdev.synth.utils.skipTextInputLayoutAnimations
import com.flatmapdev.synth.utils.textInputLayoutwithItemHint
import io.mockk.spyk
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class KeyboardFragmentTest {
    private lateinit var testComponentBuilder: DaggerTestAppComponent.Builder

    @Before
    fun setUp() {
        testComponentBuilder = DaggerTestAppComponent.builder()
    }

    @Test
    fun `it displays the keyboard scale tonic control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<KeyboardFragment>()

        onView(withId(R.id.scaleTonicLayout))
            .check(matches(textInputLayoutwithItemHint(getApp().getString(R.string.keyboard_scale_tonic))))
    }

    @Test
    fun `it displays the keyboard scale type control`() {
        getApp().appComponent = testComponentBuilder.build()
        launchFragmentInContainer<KeyboardFragment>()

        onView(withId(R.id.scaleTypeLayout))
            .check(matches(textInputLayoutwithItemHint(getApp().getString(R.string.keyboard_scale_type))))
    }

    @Test
    fun `it displays the current scale`() {
        getApp().appComponent = testComponentBuilder
            .fakeKeyboardDataModule(
                FakeKeyboardDataModule(
                    scaleAdapter = FakeScaleAdapter(
                        scale = Scale(Note.G, ScaleType.Major)
                    )
                )
            )
            .build()
        launchFragmentInContainer<KeyboardFragment>()

        onView(withText(R.string.note_g))
            .check(matches(isDisplayed()))
        onView(withText(R.string.scale_type_major))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when the keyboard scale is changed, it stores the new scale`() {
        val spyScaleAdapter = spyk(
            FakeScaleAdapter(
                Scale(Note.F, ScaleType.Major)
            )
        )
        getApp().appComponent = testComponentBuilder
            .fakeKeyboardDataModule(
                FakeKeyboardDataModule(
                    scaleAdapter = spyScaleAdapter
                )
            )
            .build()
        val scenario = launchFragmentInContainer<KeyboardFragment>()

        scenario.onFragment { fragment ->
            onView(withId(R.id.scaleTonicLayout)).perform(click())
            onView(withId(R.id.scaleTonicLayout)).perform(skipTextInputLayoutAnimations())
            assertThat(fragment.view?.findViewById<AutoCompleteTextView>(R.id.scaleTonic)?.isPopupShowing)
                .isTrue()
            onData(equalTo(getApp().getString(R.string.note_c_sharp_d_flat)))
                .inRoot(isPlatformPopup())
                .perform(click());

            onView(withId(R.id.scaleTypeLayout)).perform(click())
            onView(withId(R.id.scaleTypeLayout)).perform(skipTextInputLayoutAnimations())
            assertThat(fragment.view?.findViewById<AutoCompleteTextView>(R.id.scaleType)?.isPopupShowing)
                .isTrue()
            onData(equalTo(getApp().getString(R.string.scale_type_minor)))
                .inRoot(isPlatformPopup())
                .perform(click());
        }


        verifyOrder {
            spyScaleAdapter.storeScale(Scale(Note.C_SHARP_D_FLAT, ScaleType.Major))
            spyScaleAdapter.storeScale(Scale(Note.C_SHARP_D_FLAT, ScaleType.HarmonicMinor))
        }
    }
}
