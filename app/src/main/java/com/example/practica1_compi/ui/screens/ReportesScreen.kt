package com.example.practica1_compi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practica1_compi.viewmodel.AnalisisViewModel

@Composable
fun ReportesScreen(viewModel: AnalisisViewModel) {

    val resultado = viewModel.resultado

    if (resultado == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No se ha realizado el analisis")
        }
        return
    }

    //scroll general para toda la pantalla
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Bloque de operadores
        item {
            Column {
                Text(
                    text = "Reporte de operadores",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (resultado.operadores.isEmpty()) {
                    Text(
                        text = "No se encontraron operadores en el codigo",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    //encabezado de la tabla
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        ) {
                            Text("Operador", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                            Text("Linea", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                            Text("Columna", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                            Text("Ocurrencia", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                        }
                        HorizontalDivider()

                        //filas con los operadores
                        resultado.operadores.forEach { operadorActual ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                            ) {
                                Text(operadorActual.operador, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text(operadorActual.linea.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text(operadorActual.columna.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text(operadorActual.ocurrencia, modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        // reporte de estructuras
        item {
            Column(modifier = Modifier.padding(top = 24.dp)) {
                Text(
                    text = "Reporte de estructuras de control",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (resultado.estructuras.isEmpty()) {
                    Text(
                        text = "No se encontraron estructuras en el codigo",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    //encabezado de tabla
                    Column{
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        ) {
                            Text("Objeto", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                            Text("Linea", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                            Text("Condicion", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                        }

                        HorizontalDivider()

                        //filas con las estructuras
                        resultado.estructuras.forEach { estructuraActual ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                            ) {
                                Text(estructuraActual.tipo, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text(estructuraActual.linea.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text(estructuraActual.condicion, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                            }
                            HorizontalDivider()
                        }
                    }
                }

            }
        }
    }
}
