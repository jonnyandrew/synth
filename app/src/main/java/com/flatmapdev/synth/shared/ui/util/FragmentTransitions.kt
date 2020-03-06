package com.flatmapdev.synth.shared.ui.util

import androidx.fragment.app.Fragment
import androidx.transition.Fade

fun Fragment.applyTransitions() {
    enterTransition = Fade(Fade.IN)
    exitTransition = Fade(Fade.OUT)
    returnTransition = Fade(Fade.OUT)
}
