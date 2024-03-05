package com.intravan.salesmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intravan.salesmanagement.data.local.cached.PreferencesCached

@Dao
interface PreferencesDao {

    @Query("SELECT * FROM PreferencesCached")
    suspend fun selectAll(): List<PreferencesCached>

    @Query("SELECT * FROM PreferencesCached WHERE user_id = :userID")
    suspend fun select(userID: String): PreferencesCached

    @Query("SELECT * FROM PreferencesCached ORDER BY modified_at DESC LIMIT 1")
    suspend fun selectLast(): PreferencesCached

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(preference: PreferencesCached)
}