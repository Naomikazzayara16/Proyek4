package com.example.hanyarunrun.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hanyarunrun.viewmodel.DataViewModel

@Composable
fun AppNavHost(dataViewModel: DataViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }

        composable("entry") {
            DataEntryScreen(navController = navController, viewModel = dataViewModel)
        }

        composable("list") {
            DataListScreen(navController = navController, viewModel = dataViewModel)
        }

        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EditScreen(navController = navController, viewModel = dataViewModel, dataId = id)
        }

        // Tetap tambahkan navigasi ke halaman Profil agar bisa diakses
        composable("profile") {
            ProfileScreen(navController = navController)
        }
    }
}
