package com.example.hanyarunrun.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hanyarunrun.viewmodel.DataViewModel
import com.example.hanyarunrun.viewmodel.ProfileViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    dataViewModel: DataViewModel,
    profileViewModel: ProfileViewModel
) {
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(navController = navController, dataViewModel = dataViewModel)
        }
        composable("list") {
            DataListScreen(navController = navController, viewModel = dataViewModel)
        }
        composable("profile") {
            ProfileScreen(navController = navController, viewModel = profileViewModel)
        }
        composable("entry") {
            DataEntryScreen(navController = navController, viewModel = dataViewModel)
        }
        composable("analysis") {
            AnalysisScreen(navController = navController, viewModel = dataViewModel) // 🔹 Halaman Analisis
        }
        composable("news") {
            NewsScreen(navController = navController) // 🔹 Halaman Berita
        }
    }
}
