package com.example.hanyarunrun.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.hanyarunrun.viewmodel.ProfileViewModel
import java.io.File

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsStateWithLifecycle()

    var nama by remember { mutableStateOf(profile?.nama ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var nomorTelepon by remember { mutableStateOf(profile?.nomorTelepon ?: "") }
    var fotoPath by remember { mutableStateOf(profile?.fotoUri) }

    var showFullScreen by remember { mutableStateOf(false) } // ✅ Fullscreen view
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            fotoPath = it.toString() // ✅ Langsung update UI
            viewModel.saveProfile(nama, email, nomorTelepon, it) // ✅ Simpan ke DB
        }
    }

    LaunchedEffect(profile) {
        profile?.let {
            nama = it.nama
            email = it.email
            nomorTelepon = it.nomorTelepon
            fotoPath = it.fotoUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profil Saya", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Foto Profil + Edit Button
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.size(120.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { showFullScreen = true } // ✅ Klik untuk fullscreen
            ) {
                if (!fotoPath.isNullOrEmpty()) {
                    AsyncImage(
                        model = Uri.fromFile(File(fotoPath!!)), // ✅ Convert path ke Uri
                        contentDescription = "Foto Profil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(text = "Pilih Foto", color = Color.White)
                }
            }

            // ✅ Ikon Edit kecil
            IconButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Foto", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = nama, onValueChange = { nama = it }, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveProfile(nama, email, nomorTelepon, fotoPath?.let { Uri.parse(it) })
                Toast.makeText(context, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Profil")
        }
    }

    // ✅ Fullscreen Dialog untuk melihat foto profil
    if (showFullScreen) {
        AlertDialog(
            onDismissRequest = { showFullScreen = false },
            text = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (!fotoPath.isNullOrEmpty()) {
                        AsyncImage(
                            model = Uri.fromFile(File(fotoPath!!)),
                            contentDescription = "Foto Profil",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text("Tidak ada foto profil")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Ganti Foto")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.saveProfile(nama, email, nomorTelepon, null) // Hapus foto
                        fotoPath = null // Reset UI
                        showFullScreen = false
                    }
                ) {
                    Text("Hapus Foto", color = Color.Red)
                }
            }
        )
    }
}
