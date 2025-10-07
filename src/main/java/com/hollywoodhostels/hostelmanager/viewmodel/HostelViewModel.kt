package com.hollywoodhostels.hostelmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HostelViewModel : ViewModel() {

    private val _dashboardSummary = MutableStateFlow<DashboardSummary?>(null)
    val dashboardSummary: StateFlow<DashboardSummary?> = _dashboardSummary.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            // Simulate loading data - replace with actual repository calls later
            val summary = DashboardSummary(
                totalTenants = 0,
                totalMonthlyRent = 0.0,
                totalCollection = 0.0,
                paidTenants = 0,
                pendingTenants = 0
            )
            _dashboardSummary.value = summary
        }
    }
}

// Add this data class if it doesn't exist
data class DashboardSummary(
    val totalTenants: Int,
    val totalMonthlyRent: Double,
    val totalCollection: Double,
    val paidTenants: Int,
    val pendingTenants: Int
)