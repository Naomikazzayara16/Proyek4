package com.example.hanyarunrun.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hanyarunrun.viewmodel.DataViewModel

@Composable
fun DashboardScreen(navController: NavHostController, dataViewModel: DataViewModel) {
    val dataList by dataViewModel.dataList.observeAsState(emptyList())

    val totalData = dataList.size
    val totalKonsumsi = dataList.sumOf { it.total }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E3A8A), Color(0xFF9333EA), Color(0xFF06B6D4))
                )
            )
            .padding(16.dp),
    ) {
        // 🔹 Header Dashboard
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Selamat Datang di GODAT!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Row {
                IconButton(onClick = { /* TODO: Notifikasi */ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifikasi", tint = Color.White)
                }
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(Icons.Default.Person, contentDescription = "Profil", tint = Color.White)
                }
            }
        }

        // 🔹 Ringkasan Data
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Total Konsumsi Gula", fontSize = 18.sp, color = Color.Black)
                Text(text = "$totalKonsumsi Ton", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Total Data: $totalData", fontSize = 16.sp, color = Color.Gray)
            }
        }

        // 🔹 Navigasi Menu (Menggunakan Grid Layout)
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DashboardButton("📂 Lihat Data", Color(0xFFFF9800)) { navController.navigate("list") }
                DashboardButton("➕ Input Data", Color(0xFF4CAF50)) { navController.navigate("entry") }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DashboardButton("📊 Analisis Data", Color(0xFF2196F3)) { /* TODO: Halaman Analisis */ }
                DashboardButton("📰 Berita", Color(0xFFE91E63)) { /* TODO: Halaman Berita */ }
            }
        }
    }
}

// 🔹 Tombol Dashboard Berbentuk Kartu
@Composable
fun DashboardButton(title: String, color: Color, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(150.dp, 80.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
