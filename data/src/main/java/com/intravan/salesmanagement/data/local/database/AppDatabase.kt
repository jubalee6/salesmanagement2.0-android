package com.intravan.salesmanagement.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intravan.salesmanagement.data.local.cached.CompanyItemCached
import com.intravan.salesmanagement.data.local.dao.CompanyDao

private const val DATABASE_VERSION = 24040901

@Database(
    entities = [
        CompanyItemCached::class
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CompanyDao(): CompanyDao
}