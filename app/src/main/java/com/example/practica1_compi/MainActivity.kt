package com.example.practica1_compi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica1_compi.ui.screens.MainScreen
import com.example.practica1_compi.ui.theme.Practica1_compiTheme
import com.example.practica1_compi.viewmodel.AnalisisViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Practica1_compiTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenWithViewModel()
                }
            }
        }
    }
}

@Composable
fun MainScreenWithViewModel(
    viewModel: AnalisisViewModel = viewModel()
) {
    MainScreen(viewModel = viewModel)
}