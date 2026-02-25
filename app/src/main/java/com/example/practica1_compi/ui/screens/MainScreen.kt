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
                    .padding(horizontal = 4.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Button(
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Editor",
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Button(
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Reportes",
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Button(
                    onClick = { selectedTab = 2 },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Diagrama",
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Button(
                    onClick = { selectedTab = 3 },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Errores",
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
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
                0 -> EditorScreen(viewModel)
                1 -> ReportesScreen(viewModel)
                2 -> DiagramaScreen()
                3 -> ErroresScreen(viewModel)
            }
        }
    }
}