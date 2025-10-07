package com.hollywoodhostels.hostelmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.lifecycle.ViewModelProvider
import com.hollywoodhostels.hostelmanager.viewmodel.HostelViewModel
import com.hollywoodhostels.hostelmanager.viewmodel.HostelViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HostelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        val factory = HostelViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[HostelViewModel::class.java]

        setupNavigation()
    }

    private fun setupNavigation() {
        // Find the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        // Find BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Setup AppBarConfiguration with top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_tenants,
                R.id.navigation_payments
            )
        )

        // Connect everything
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}