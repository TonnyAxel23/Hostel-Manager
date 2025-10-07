package com.hollywoodhostels.hostelmanager.data.repository

data class DashboardSummary(
    val totalTenants: Int,
    val totalMonthlyRent: Double,
    val totalCollection: Double,
    val paidTenants: Int,
    val pendingTenants: Int
)