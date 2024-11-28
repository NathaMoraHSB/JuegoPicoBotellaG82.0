package com.example.juegopicobotellag8.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.juegopicobotellag8.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Show LoginFragment
        if (savedInstanceState == null) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationContainer) as NavHostFragment
            val navController = navHostFragment.navController
            navController.setGraph(R.navigation.nav_graph)
        }
    }
}