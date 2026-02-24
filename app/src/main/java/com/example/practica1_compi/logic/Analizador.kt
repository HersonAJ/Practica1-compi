package com.example.practica1_compi.logic

import com.example.practica1_compi.analizadores.Lexer
import com.example.practica1_compi.analizadores.Parser
import java.io.StringReader

data class ResultadoAnalisis(
    val erroresLexicos: List<String>,
    val erroresSintacticos: List<String>
) {
    val hayErroresLexicos: Boolean
        get() = erroresLexicos.isNotEmpty()

    val hayErroresSintacticos: Boolean
        get() = erroresSintacticos.isNotEmpty()

    val esExitoso: Boolean
        get() = !hayErroresLexicos && !hayErroresSintacticos
}

class Analizador {

    fun analyze(text: String): ResultadoAnalisis {

        return try {

            val lexer = Lexer(StringReader(text))
            val parser = Parser(lexer)

            parser.parse()

            ResultadoAnalisis(
                erroresLexicos = lexer.lexicalErrors,
                erroresSintacticos = parser.syntaxErrors
            )

        } catch (e: Exception) {

            ResultadoAnalisis(
                erroresLexicos = emptyList(),
                erroresSintacticos = listOf("Excepcion: ${e.message}")
            )
        }
    }
}