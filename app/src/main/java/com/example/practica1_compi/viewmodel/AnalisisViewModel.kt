package com.example.practica1_compi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.practica1_compi.logic.Analizador
import com.example.practica1_compi.logic.ResultadoAnalisis


class AnalisisViewModel: ViewModel() {

    private val analizador = Analizador()

    var resultado by mutableStateOf<ResultadoAnalisis?>(null)
        private set

    var puedeGenerarDiagrama by mutableStateOf(false)
        private set

    var puedeVerReportes by mutableStateOf(false)
        private set

    fun analizarTexto(texto: String) {
        val resultadoAnalisis = analizador.analyze(texto)

        resultado = resultadoAnalisis

        puedeGenerarDiagrama = resultadoAnalisis.esExitoso

        puedeVerReportes = resultadoAnalisis.hayErroresLexicos || resultadoAnalisis.hayErroresSintacticos || resultadoAnalisis.esExitoso
    }
}