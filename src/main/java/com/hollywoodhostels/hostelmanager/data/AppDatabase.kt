package com.hollywoodhostels.hostelmanager.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.hollywoodhostels.hostelmanager.data.entities.Tenant
import com.hollywoodhostels.hostelmanager.data.entities.Payment
import com.hollywoodhostels.hostelmanager.data.dao.TenantDao
import com.hollywoodhostels.hostelmanager.data.dao.PaymentDao

@Database(
    entities = [Tenant::class, Payment::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class) // Add this annotation
abstract class AppDatabase : RoomDatabase() {
    abstract fun tenantDao(): TenantDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hostel_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}