package com.hollywoodhostels.hostelmanager.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.hollywoodhostels.hostelmanager.data.entities.Tenant

@Dao
interface TenantDao {
    @Query("SELECT * FROM tenants ORDER BY roomNumber")
    fun getAllTenants(): Flow<List<Tenant>>

    @Insert
    suspend fun insertTenant(tenant: Tenant): Long

    @Update
    suspend fun updateTenant(tenant: Tenant)

    @Delete
    suspend fun deleteTenant(tenant: Tenant)
}
