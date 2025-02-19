package com.example.hanyarunrun.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val nama: String,
    val email: String,
    val nomorTelepon: String,
    val fotoUri: String?
)
