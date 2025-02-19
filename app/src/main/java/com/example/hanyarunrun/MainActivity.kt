package com.example.hanyarunrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hanyarunrun.ui.AppNavHost
import com.example.hanyarunrun.viewmodel.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataViewModel: DataViewModel = viewModel()

            // Kembalikan seperti sebelum CRUD Profile
            AppNavHost(dataViewModel = dataViewModel)
        }
    }
}
