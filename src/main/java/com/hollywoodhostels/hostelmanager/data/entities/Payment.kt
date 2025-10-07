package com.hollywoodhostels.hostelmanager.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "payments",
    foreignKeys = [ForeignKey(
        entity = Tenant::class,
        parentColumns = ["id"],
        childColumns = ["tenantId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tenantId: Long,
    val amount: Double,
    val paymentDate: Date, // Room will use TypeConverter for this
    val month: String,
    val year: Int,
    val paymentMethod: String,
    val isPaid: Boolean = true
)
