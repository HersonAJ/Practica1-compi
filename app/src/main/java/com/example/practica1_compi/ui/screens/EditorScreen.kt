package com.example.practica1_compi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica1_compi.viewmodel.AnalisisViewModel

@Composable
fun EditorScreen(
    viewModel: AnalisisViewModel = viewModel()
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
                Text("Compilacion exitosa")
            } else {

                Text("Se encontraron errores")

                Spacer(modifier = Modifier.height(8.dp))

                if (resultado.erroresLexicos.isNotEmpty()) {
                    Text("Errores Lexicos:")
                    resultado.erroresLexicos.forEach {
                        Text(it)
                    }
                }

                if (resultado.erroresSintacticos.isNotEmpty()) {
                    Text("Errores Sintacticos:")
                    resultado.erroresSintacticos.forEach {
                        Text(it)
                    }
                }
            }
        }
    }
}

