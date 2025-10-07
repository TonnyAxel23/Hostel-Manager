package com.hollywoodhostels.hostelmanager.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.hollywoodhostels.hostelmanager.data.entities.Payment

@Dao
interface PaymentDao {
    @Insert
    suspend fun insertPayment(payment: Payment): Long

    @Query("SELECT * FROM payments WHERE tenantId = :tenantId ORDER BY paymentDate DESC")
    fun getPaymentsForTenant(tenantId: Long): Flow<List<Payment>>
}
