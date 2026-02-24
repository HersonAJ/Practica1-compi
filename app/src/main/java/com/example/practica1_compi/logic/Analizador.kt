package com.example.practica1_compi.logic

import com.example.practica1_compi.analizadores.Lexer
import com.example.practica1_compi.analizadores.Parser
import com.example.practica1_compi.models.ErrorReporte
import com.example.practica1_compi.models.ResultadoAnalisis
import java.io.StringReader

class Analizador {

    fun analyze(text: String): ResultadoAnalisis {

        return try {

            val lexer = Lexer(StringReader(text))
            val parser = Parser(lexer)

            parser.parse()

            val erroresTotales = lexer.errorReportes + parser.errorReportes

            ResultadoAnalisis( errores = erroresTotales)

        } catch (e: Exception) {

            ResultadoAnalisis(
                errores = listOf(
                    ErrorReporte(
                        lexema = "",
                        linea = 0,
                        columna = 0,
                        tipo = "sintactico",
                        descripcion = "Excepcion: ${e.message}"
                    )
                )
            )
        }
    }
}