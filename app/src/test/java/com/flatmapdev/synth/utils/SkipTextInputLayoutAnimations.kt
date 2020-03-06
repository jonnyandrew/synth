package com.flatmapdev.synth.utils

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matcher

fun skipTextInputLayoutAnimations(): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextInputLayout::class.java)
        }

        override fun getDescription(): String {
            return "Skips any animations."
        }

        override fun perform(uiController: UiController?, view: View) {
            view.jumpDrawablesToCurrentState()
        }
    }
}