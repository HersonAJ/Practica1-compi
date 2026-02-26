package com.example.practica1_compi.logic

import android.util.Log
import com.example.practica1_compi.analizadores.Lexer
import com.example.practica1_compi.analizadores.Parser
import com.example.practica1_compi.models.ErrorReporte
import com.example.practica1_compi.models.NodoPrograma
import com.example.practica1_compi.models.ResultadoAnalisis
import java.io.StringReader

class Analizador {


    fun analyze(texto: String): ResultadoAnalisis {
        return try {

            val lexer = Lexer(StringReader(texto))
            val parser = Parser(lexer)

            if (lexer.errorReportes.isNotEmpty()) {
                lexer.errorReportes.forEach { error ->
                }
            }

            val resultadoParse = parser.parse()


            if (parser.errorReportes.isNotEmpty()) {
                parser.errorReportes.forEach { error ->
                }
            }

            // Verificacion de resultadoParse null
            if (resultadoParse == null) {
                val errores = mutableListOf<ErrorReporte>()
                errores.addAll(lexer.errorReportes)
                errores.addAll(parser.errorReportes)

                if (errores.isEmpty()) {
                    errores.add(
                        ErrorReporte(
                            lexema = "SISTEMA",
                            linea = 0,
                            columna = 0,
                            tipo = "sistema",
                            descripcion = "El parser retorno null sin errores registrados"
                        )
                    )
                }

                return ResultadoAnalisis(errores = errores)
            }

            // Intentar obtener el programa de manera segura
            val programa = when (val value = resultadoParse.value) {
                is NodoPrograma -> {
                    value
                }
                else -> {
                    null
                }
            }

            val erroresTotales = mutableListOf<ErrorReporte>()
            erroresTotales.addAll(lexer.errorReportes)
            erroresTotales.addAll(parser.errorReportes)


            if (erroresTotales.isNotEmpty() || programa == null) {
                return ResultadoAnalisis(errores = erroresTotales)
            }

            // Procesar operadores y estructuras
            val operadoresTotales = lexer.operadores
            val extractor = ExtractorReporteEstructuras()
            val estructurasTotales = extractor.extraer(programa)

            val lineas = texto.lines()
            val operadoresConOcurrencia = operadoresTotales.map { operadorActual ->
                val ocurrenciaDetallada = ConstructorOcurrencia.construirOcurrencia(
                    lineas,
                    operadorActual.linea,
                    operadorActual.columna
                )
                operadorActual.copy(ocurrencia = ocurrenciaDetallada)
            }

            return ResultadoAnalisis(
                errores = erroresTotales,
                operadores = operadoresConOcurrencia,
                estructuras = estructurasTotales,
                programa = programa
            )

        } catch (e: Exception) {
            return ResultadoAnalisis(
                errores = listOf(
                    ErrorReporte(
                        lexema = "",
                        linea = 0,
                        columna = 0,
                        tipo = "sistema",
                        descripcion = "Excepcion: ${e.message}\n${e.stackTraceToString()}"
                    )
                )
            )
        }
    }
}