package com.hollywoodhostels.hostelmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tenants")
data class Tenant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val roomNumber: String,
    val phoneNumber: String,
    val email: String,
    val checkInDate: Date, // Room will use TypeConverter for this
    val rentAmount: Double,
    val securityDeposit: Double
)