package com.example.hanyarunrun.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hanyarunrun.viewmodel.DataViewModel
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.BarChartStyle
import com.github.tehras.charts.bar.renderer.SimpleBarChartRenderer

@Composable
fun AnalysisScreen(navController: NavHostController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.collectAsStateWithLifecycle(initialValue = emptyList())

    // Jika data masih kosong, tampilkan teks
    if (dataList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Belum ada data untuk dianalisis", fontSize = 16.sp, color = Color.Gray)
        }
        return
    }

    // Mengelompokkan data berdasarkan tahun
    val dataGrouped = remember(dataList) {
        dataList.groupBy { it.tahun }
            .mapValues { (_, entries) -> entries.sumOf { it.total } }
    }

    // Siapkan data untuk grafik
    val barChartData = dataGrouped.map { (tahun, total) ->
        BarChartData.Bar(
            label = tahun.toString(),
            value = total.toFloat(),
            color = Color(0xFF4CAF50)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Analisis Data", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        BarChart(
            barChartData = BarChartData(bars = barChartData),
            barChartStyle = BarChartStyle(
                barWidth = 40.dp,
                barSpacing = 16.dp,
                barRenderer = SimpleBarChartRenderer()
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("dashboard") }) {
            Text("Kembali ke Dashboard")
        }
    }
}
