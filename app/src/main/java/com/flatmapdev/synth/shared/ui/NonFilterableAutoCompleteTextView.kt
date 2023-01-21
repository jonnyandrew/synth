package com.flatmapdev.synth.shared.ui

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.flatmapdev.synth.R

/**
 * Needed due to a bug in AutoCompleteTextView.
 * See https://github.com/material-components/material-components-android/issues/1464
 */
class NonFilterableAutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.autoCompleteTextViewStyle
) :
    AppCompatAutoCompleteTextView(context, attributeSet, defStyleAttr) {
    private var isCallingSetText = false

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (isCallingSetText || inputType != EditorInfo.TYPE_NULL) {
            super.setText(text, type)
        } else {
            isCallingSetText = true
            setText(text, false)
            isCallingSetText = false
        }
    }
}
