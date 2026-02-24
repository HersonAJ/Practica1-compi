package com.example.practica1_compi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practica1_compi.viewmodel.AnalisisViewModel

@Composable
fun EditorScreen(
    viewModel: AnalisisViewModel
) {

    var texto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Editor de codigo")

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Escribe el codigo aqui....") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.analizarTexto(texto) }
        ) {
            Text("Analizar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.resultado?.let { resultado ->

            Spacer(modifier = Modifier.height(16.dp))

            if (resultado.esExitoso) {
                Text("Compilacion Exitosa")
            } else {
                Text("Se encontraron errores")
            }
            }
        }
    }