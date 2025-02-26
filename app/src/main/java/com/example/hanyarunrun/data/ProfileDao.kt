package com.example.hanyarunrun.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_table LIMIT 1")
    fun getProfile(): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // ✅ Jika ada, replace data
    suspend fun insert(profile: ProfileEntity)

    @Query("DELETE FROM profile_table")
    suspend fun deleteAll()
    @Query("SELECT * FROM profile_table LIMIT 1")
    suspend fun getProfileSync(): ProfileEntity?
}
