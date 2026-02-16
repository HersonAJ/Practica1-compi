package com.example.practica1_compi.models

data class Token(
    val tipo: String,
    val lexema: String,
    val linea: Int,
    val columna: Int
)
