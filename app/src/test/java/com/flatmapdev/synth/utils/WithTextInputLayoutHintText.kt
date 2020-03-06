package com.flatmapdev.synth.utils

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

fun textInputLayoutwithItemHint(matcherText: String): Matcher<View?>? {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with item hint: $matcherText")
        }

        override fun matchesSafely(editTextField: TextInputLayout): Boolean {
            return matcherText.equals(editTextField.hint.toString(), ignoreCase = true)
        }
    }
}
