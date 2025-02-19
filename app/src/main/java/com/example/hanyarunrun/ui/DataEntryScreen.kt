package com.example.hanyarunrun.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hanyarunrun.viewmodel.DataViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataEntryScreen(navController: NavHostController, viewModel: DataViewModel) {
    val context = LocalContext.current

    var selectedProvinsi by remember { mutableStateOf("Jawa Barat") } // Hanya Jawa Barat
    var kodeProvinsi by remember { mutableStateOf("32") } // Kode Jawa Barat
    var selectedKabupaten by remember { mutableStateOf("") }
    var kodeKabupaten by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    var expandedKabupaten by remember { mutableStateOf(false) }

    // List Kabupaten/Kota di Jawa Barat
    val kabupatenList = listOf(
        "Bandung" to "3204",
        "Bekasi" to "3216",
        "Bogor" to "3201",
        "Cirebon" to "3209",
        "Depok" to "3276"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Entry Data",
                style = MaterialTheme.typography.headlineMedium
            )

            // Nama Provinsi (Otomatis: Jawa Barat)
            OutlinedTextField(
                value = selectedProvinsi,
                onValueChange = {},
                label = { Text("Nama Provinsi") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Kode Provinsi (Otomatis)
            OutlinedTextField(
                value = kodeProvinsi,
                onValueChange = {},
                label = { Text("Kode Provinsi") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Dropdown Kabupaten/Kota
            ExposedDropdownMenuBox(
                expanded = expandedKabupaten,
                onExpandedChange = { expandedKabupaten = !expandedKabupaten }
            ) {
                OutlinedTextField(
                    value = selectedKabupaten,
                    onValueChange = {},
                    label = { Text("Nama Kabupaten/Kota") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedKabupaten,
                    onDismissRequest = { expandedKabupaten = false }
                ) {
                    kabupatenList.forEach { (nama, kode) ->
                        DropdownMenuItem(
                            text = { Text(nama) },
                            onClick = {
                                selectedKabupaten = nama
                                kodeKabupaten = kode
                                expandedKabupaten = false
                            }
                        )
                    }
                }
            }

            // Kode Kabupaten/Kota (Otomatis)
            OutlinedTextField(
                value = kodeKabupaten,
                onValueChange = {},
                label = { Text("Kode Kabupaten/Kota") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Total
            OutlinedTextField(
                value = total,
                onValueChange = { total = it },
                label = { Text("Total") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Satuan
            OutlinedTextField(
                value = satuan,
                onValueChange = { satuan = it },
                label = { Text("Satuan") },
                modifier = Modifier.fillMaxWidth()
            )

            // Tahun
            OutlinedTextField(
                value = tahun,
                onValueChange = { tahun = it },
                label = { Text("Tahun") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Tombol Submit
            Button(
                onClick = {
                    if (selectedKabupaten.isNotEmpty() && total.isNotEmpty() && satuan.isNotEmpty() && tahun.isNotEmpty()) {
                        viewModel.insertData(
                            kodeProvinsi = kodeProvinsi,
                            namaProvinsi = selectedProvinsi,
                            kodeKabupatenKota = kodeKabupaten,
                            namaKabupatenKota = selectedKabupaten,
                            total = total,
                            satuan = satuan,
                            tahun = tahun
                        )
                        Toast.makeText(context, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                        navController.navigate("list") // Kembali ke daftar
                    } else {
                        Toast.makeText(context, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Data")
            }

            // Tombol Lihat Data
            Button(
                onClick = { navController.navigate("list") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lihat Data")
            }
        }
    }
}
