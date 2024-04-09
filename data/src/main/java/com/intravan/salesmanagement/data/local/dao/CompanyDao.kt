package com.intravan.salesmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.intravan.salesmanagement.data.local.cached.CompanyItemCached

@Dao
interface CompanyDao {

    @Query("SELECT * FROM CompanyItemCached")
    suspend fun selectAll(): List<CompanyItemCached>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<CompanyItemCached>)

    @Query("Delete FROM CompanyItemCached")
    fun deleteAll()

    @Transaction
    suspend fun deleteAllAndInsert(items: List<CompanyItemCached>) {
        deleteAll()
        insert(items)
    }
}