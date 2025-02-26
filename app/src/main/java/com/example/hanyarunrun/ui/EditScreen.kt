package com.example.hanyarunrun.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hanyarunrun.data.DataEntity
import com.example.hanyarunrun.viewmodel.DataViewModel

@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: DataViewModel,
    dataId: Int
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) } // 🔹 State untuk dialog konfirmasi hapus

    var kodeProvinsi by remember { mutableStateOf("") }
    var namaProvinsi by remember { mutableStateOf("") }
    var kodeKabupatenKota by remember { mutableStateOf("") }
    var namaKabupatenKota by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }

    LaunchedEffect(dataId) {
        viewModel.getDataById(dataId)?.let { data ->
            kodeProvinsi = data.kodeProvinsi
            namaProvinsi = data.namaProvinsi
            kodeKabupatenKota = data.kodeKabupatenKota
            namaKabupatenKota = data.namaKabupatenKota
            total = data.total.toString()
            satuan = data.satuan
            tahun = data.tahun.toString()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Edit Data", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = total, onValueChange = { total = it }, label = { Text("Total") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = satuan, onValueChange = { satuan = it }, label = { Text("Satuan") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tahun, onValueChange = { tahun = it }, label = { Text("Tahun") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                val updatedData = DataEntity(
                    id = dataId,
                    kodeProvinsi = kodeProvinsi,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota,
                    total = total.toDoubleOrNull() ?: 0.0,
                    satuan = satuan,
                    tahun = tahun.toIntOrNull() ?: 0
                )
                viewModel.updateData(updatedData)
                Toast.makeText(context, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Data")
        }

        // 🔹 Tombol Hapus dengan Konfirmasi Dialog
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hapus Data")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Konfirmasi Hapus") },
                text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteDataById(dataId)
                            Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
                            showDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("Ya, Hapus")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}
