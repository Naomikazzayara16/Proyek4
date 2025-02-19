package com.example.hanyarunrun.data

import androidx.room.*

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_table LIMIT 1")
    fun getProfile(): kotlinx.coroutines.flow.Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: ProfileEntity) // Menyimpan atau memperbarui data

    @Query("DELETE FROM profile_table")
    suspend fun deleteAll() // Menghapus semua data profil
}
