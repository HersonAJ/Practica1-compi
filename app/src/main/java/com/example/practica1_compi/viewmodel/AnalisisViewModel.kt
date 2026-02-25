package com.example.practica1_compi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.practica1_compi.logic.Analizador
import com.example.practica1_compi.models.ErrorReporte
import com.example.practica1_compi.models.ResultadoAnalisis


class AnalisisViewModel: ViewModel() {

    private val analizador = Analizador()

    var codigoFuente by mutableStateOf("")
    private set

    fun actualizarCodigo(nuevoTexto: String){
        codigoFuente = nuevoTexto
    }

    var resultado by mutableStateOf<ResultadoAnalisis?>(null)
        private set

    var puedeGenerarDiagrama by mutableStateOf(false)
        private set

    var puedeVerReportes by mutableStateOf(false)
        private set

    fun analizarTexto(texto: String) {
        val resultadoAnalisis = analizador.analyze(texto)

        if (texto.isBlank()) {
            resultado = ResultadoAnalisis(
            errores = listOf(
                ErrorReporte(
                    lexema = "",
                    linea = 0,
                    columna = 0,
                    tipo = "Sintactico",
                    descripcion = "El editor esta vacio, no hay nada que analizar"
                )
            )
            )
            return
        }

        resultado = resultadoAnalisis

        puedeGenerarDiagrama = resultadoAnalisis.esExitoso

        puedeVerReportes = true
    }
}