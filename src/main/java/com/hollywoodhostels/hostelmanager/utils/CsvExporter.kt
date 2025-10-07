package com.hollywoodhostels.hostelmanager.utils

import android.content.Context
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.hollywoodhostels.hostelmanager.data.entities.Tenant
import com.hollywoodhostels.hostelmanager.data.repository.DashboardSummary
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object CsvExporter {

    fun exportTenantsData(context: Context, tenants: List<Tenant>): File {
        val file = FileUtils.createExportFile(context, "tenants_${System.currentTimeMillis()}.csv")

        csvWriter().open(file) {
            // Write header
            writeRow(listOf("ID", "Name", "Room Number", "Phone", "Email", "Check-in Date", "Rent Amount", "Security Deposit"))

            // Write data
            tenants.forEach { tenant ->
                writeRow(listOf(
                    tenant.id.toString(),
                    tenant.name,
                    tenant.roomNumber,
                    tenant.phoneNumber,
                    tenant.email,
                    SimpleDateFormat("yyyy-MM-dd").format(tenant.checkInDate),
                    tenant.rentAmount.toString(),
                    tenant.securityDeposit.toString()
                ))
            }
        }

        return file
    }

    fun exportDashboard(context: Context, summary: DashboardSummary?): File {
        val file = FileUtils.createExportFile(context, "dashboard_summary_${System.currentTimeMillis()}.csv")

        csvWriter().open(file) {
            writeRow(listOf("Dashboard Summary - ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}"))
            writeRow(listOf("Metric", "Value"))
            writeRow(listOf("Total Tenants", summary?.totalTenants?.toString() ?: "0"))
            writeRow(listOf("Total Monthly Rent", "₹${summary?.totalMonthlyRent ?: 0.0}"))
            writeRow(listOf("Total Collection", "₹${summary?.totalCollection ?: 0.0}"))
            writeRow(listOf("Paid Tenants", summary?.paidTenants?.toString() ?: "0"))
            writeRow(listOf("Pending Tenants", summary?.pendingTenants?.toString() ?: "0"))
        }

        return file
    }
}