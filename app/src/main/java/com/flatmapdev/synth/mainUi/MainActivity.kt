package com.flatmapdev.synth.mainUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.flatmapdev.synth.R
import com.flatmapdev.synth.app.App
import com.flatmapdev.synth.engineCore.adapter.SynthEngineAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var synthEngineAdapter: SynthEngineAdapter

    private val navController: NavController
        get() = findNavController(R.id.navHostFragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onResume() {
        super.onResume()
        synthEngineAdapter.start()
    }

    override fun onPause() {
        synthEngineAdapter.stop()
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
