package com.example.hanyarunrun.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hanyarunrun.data.AppDatabase
import com.example.hanyarunrun.data.ProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileDao = AppDatabase.getDatabase(application).profileDao()
    private val context: Context = application.applicationContext

    private val _profile = MutableStateFlow<ProfileEntity?>(null)
    val profile = _profile.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val savedProfile = profileDao.getProfileSync()
            _profile.value = savedProfile
        }
    }

    fun saveProfile(nama: String, email: String, nomorTelepon: String, fotoUri: Uri?) {
        viewModelScope.launch {
            val existingProfile = profileDao.getProfileSync()

            // ✅ Simpan gambar dan dapatkan path-nya
            val savedPhotoPath = fotoUri?.let { saveImageToInternalStorage(it) }

            val profile = ProfileEntity(
                id = existingProfile?.id ?: 1, // Gunakan ID tetap agar tidak ada duplikasi
                nama = nama,
                email = email,
                nomorTelepon = nomorTelepon,
                fotoUri = savedPhotoPath ?: existingProfile?.fotoUri // Gunakan yang lama jika tidak ada yang baru
            )

            profileDao.insert(profile)
            _profile.value = profile // ✅ Update state agar UI langsung berubah
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            profileDao.deleteAll()
            _profile.value = null
        }
    }

    /**
     * ✅ Simpan gambar ke penyimpanan internal dan kembalikan path file-nya.
     */
    private fun saveImageToInternalStorage(imageUri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val file = File(context.filesDir, "profile_image.jpg") // Simpan dengan nama tetap
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            file.absolutePath // ✅ Kembalikan path file yang tersimpan
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
