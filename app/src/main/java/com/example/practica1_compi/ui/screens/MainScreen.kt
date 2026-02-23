package com.example.practica1_compi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.practica1_compi.viewmodel.AnalisisViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    viewModel: AnalisisViewModel
) {

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = { selectedTab = 0 }
                ) {
                    Text("Editor")
                }

                Button(
                    onClick = { selectedTab = 1 }
                ) {
                    Text("Reportes")
                }

                Button(
                    onClick = { selectedTab = 2 }
                ) {
                    Text("Diagrama")
                }

                Button(
                    onClick = { selectedTab = 3 }
                ) {
                    Text("Errores")
                }
            }
        }
    ){ paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when (selectedTab) {
                0 -> EditorScreen()
                1 -> ReportesScreen()
                2 -> DiagramaScreen()
                3 -> ErroresScreen()
            }
        }
    }
}