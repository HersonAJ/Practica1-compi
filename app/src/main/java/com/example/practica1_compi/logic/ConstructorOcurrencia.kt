package com.example.practica1_compi.logic

import androidx.compose.ui.util.fastCoerceAtMost


//object lo vuelve singleton
object ConstructorOcurrencia {

    fun construirOcurrencia( lineas: List<String>, linea: Int, columna: Int, rango: Int = 2): String {

        val textoLinea = lineas.getOrNull(linea - 1) ?: return ""
        val inicio = (columna - rango).coerceAtLeast(0)
        val fin = (columna + rango).coerceAtMost(textoLinea.length)
        return textoLinea.substring(inicio, fin)
    }
}