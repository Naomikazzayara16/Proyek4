package com.example.hanyarunrun.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String = "",
    val email: String = "",
    val nomorTelepon: String = "",
    val fotoUri: String? = null // Default null agar tidak error jika kosong
)