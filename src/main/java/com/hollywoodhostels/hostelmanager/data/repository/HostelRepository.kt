package com.hollywoodhostels.hostelmanager.data.repository

import kotlinx.coroutines.flow.Flow
import com.hollywoodhostels.hostelmanager.data.dao.TenantDao
import com.hollywoodhostels.hostelmanager.data.dao.PaymentDao
import com.hollywoodhostels.hostelmanager.data.entities.Tenant
import com.hollywoodhostels.hostelmanager.data.entities.Payment

class HostelRepository(
    private val tenantDao: TenantDao,
    private val paymentDao: PaymentDao
) {

    fun getAllTenants(): Flow<List<Tenant>> = tenantDao.getAllTenants()

    suspend fun addTenant(tenant: Tenant): Long = tenantDao.insertTenant(tenant)

    suspend fun addPayment(payment: Payment): Long = paymentDao.insertPayment(payment)

    fun getPaymentsForTenant(tenantId: Long): Flow<List<Payment>> =
        paymentDao.getPaymentsForTenant(tenantId)
}