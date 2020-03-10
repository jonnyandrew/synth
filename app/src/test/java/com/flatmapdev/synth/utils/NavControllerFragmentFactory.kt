package com.flatmapdev.synth.utils

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider

class NavControllerFragmentFactory(
    @NavigationRes private val navGraph: Int? = null,
    @IdRes private val destinationId: Int? = null
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return super.instantiate(classLoader, className).apply {
            val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext()
            )

            if (navGraph != null) {
                navController.setGraph(navGraph)
            }

            if (destinationId != null) {
                navController.setCurrentDestination(destinationId)
            }

            viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(requireView(), navController)
                }
            }
        }
    }
}
