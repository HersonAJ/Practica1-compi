package com.example.practica1_compi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practica1_compi.viewmodel.AnalisisViewModel

@Composable
fun ErroresScreen(
    viewModel: AnalisisViewModel
) {

    val resultado = viewModel.resultado

    if (resultado == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No se ha realizado ningun analisis.")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Reporte de Analisis",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (resultado.esExitoso) {

            Text(
                text = "Compilacion exitosa",
                style = MaterialTheme.typography.bodyLarge
            )

        } else {

            Text(
                text = "Se encontraron errores:",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {

                //encabezado
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Lexema", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text("Linea", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text("Columna", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text("Tipo", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text("Descripcion", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                    }

                    HorizontalDivider()
                }

                //filas de errores
                items(resultado.errores) { error ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Text(error.lexema, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text(error.linea.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text(error.columna.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text(error.tipo, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text(error.descripcion, modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                    }

                    HorizontalDivider()
                }
            }
        }
    }
}