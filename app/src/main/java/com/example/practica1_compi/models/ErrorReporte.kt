package com.example.practica1_compi.models

data class ErrorReporte(
    val lexema: String,
    val linea: Int,
    val columna: Int,
    val tipo: String,
    val descripcion: String
)
