package com.example.hanyarunrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.hanyarunrun.ui.AppNavHost
import com.example.hanyarunrun.viewmodel.DataViewModel
import com.example.hanyarunrun.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController() // ✅ Pastikan NavController tersedia
            val dataViewModel: DataViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()

            // ✅ Pastikan semua parameter dikirim dengan benar ke AppNavHost
            AppNavHost(
                navController = navController,
                dataViewModel = dataViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}
