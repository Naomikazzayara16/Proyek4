package com.example.hanyarunrun.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.hanyarunrun.viewmodel.DataViewModel
import com.example.hanyarunrun.viewmodel.ProfileViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    dataViewModel: DataViewModel,
    profileViewModel: ProfileViewModel
) {
    Scaffold(
        bottomBar = { CustomBottomNav(navController) }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            dataViewModel = dataViewModel,
            profileViewModel = profileViewModel
        ) // ✅ Memastikan parameter diteruskan dengan benar
    }
}

@Composable
fun CustomBottomNav(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, "dashboard"),
        BottomNavItem("Keranjang", Icons.Filled.ShoppingCart, "keranjang"),
        BottomNavItem("Profile", Icons.Filled.Person, "profile")
    )

    NavigationBar {
        items.forEach { item ->
            val isSelected = navController.currentDestination?.route == item.route
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)
