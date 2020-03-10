package com.flatmapdev.synth.utils

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import com.flatmapdev.synth.R

inline fun <reified F : Fragment> launchAndSetUpFragment(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.AppTheme,
    fragmentFactory: FragmentFactory = FragmentFactory()
): FragmentScenario<F> {
    return FragmentScenario.launchInContainer(
        F::class.java,
        fragmentArgs,
        themeResId,
        fragmentFactory
    )
}
