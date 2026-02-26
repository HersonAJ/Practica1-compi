package com.example.practica1_compi.models

data class ResultadoAnalisis(
    val errores: List<ErrorReporte>,
    val operadores: List<ReporteOperador> = emptyList(),
    val estructuras: List<ReporteEstructura> = emptyList(),
    val programa: NodoPrograma? = null) {

    val hayErrores: Boolean
        get() = errores.isNotEmpty()

    val esExitoso: Boolean
        get() = errores.isEmpty() && programa != null
}
