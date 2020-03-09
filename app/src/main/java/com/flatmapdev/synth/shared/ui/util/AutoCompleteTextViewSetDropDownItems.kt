package com.flatmapdev.synth.shared.ui.util

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.flatmapdev.synth.R

fun AutoCompleteTextView.setDropDownItems(items: List<String>) {
    val adapter: ArrayAdapter<String> = ArrayAdapter(
        context,
        R.layout.view_dropdown_menu_item,
        items
    )

    setAdapter(adapter)
}
