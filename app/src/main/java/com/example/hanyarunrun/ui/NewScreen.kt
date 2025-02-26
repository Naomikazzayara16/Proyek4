package com.example.hanyarunrun.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NewsScreen(navController: NavHostController) {
    val newsList = listOf(
        NewsItem("Konsumsi Gula Meningkat di 2024", "Peningkatan konsumsi gula di Jawa Barat mencapai 10% lebih tinggi dari tahun lalu."),
        NewsItem("Pemprov Jabar Rilis Data Terbaru", "Data konsumsi gula per kota telah diperbarui untuk tahun 2024."),
        NewsItem("Kebijakan Baru untuk Pengelolaan Gula", "Pemerintah mengumumkan regulasi baru dalam distribusi gula."),
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Berita Terkini", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(newsList) { news ->
                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = news.title, fontSize = 18.sp, fontWeight = MaterialTheme.typography.bodyLarge.fontWeight)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = news.description, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("dashboard") }) {
            Text("Kembali ke Dashboard")
        }
    }
}

// Model Data untuk Berita
data class NewsItem(val title: String, val description: String)
