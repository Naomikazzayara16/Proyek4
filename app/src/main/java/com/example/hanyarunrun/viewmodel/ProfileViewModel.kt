package com.example.hanyarunrun.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _nama = MutableStateFlow("")
    val nama: StateFlow<String> = _nama

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _nomorTelepon = MutableStateFlow("")
    val nomorTelepon: StateFlow<String> = _nomorTelepon

    private val _fotoUri = MutableStateFlow<String?>(null)
    val fotoUri: StateFlow<String?> = _fotoUri

    fun saveProfile(nama: String, email: String, nomorTelepon: String, fotoUri: String?) {
        viewModelScope.launch {
            _nama.value = nama
            _email.value = email
            _nomorTelepon.value = nomorTelepon
            _fotoUri.value = fotoUri
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            _nama.value = ""
            _email.value = ""
            _nomorTelepon.value = ""
            _fotoUri.value = null
        }
    }
}
