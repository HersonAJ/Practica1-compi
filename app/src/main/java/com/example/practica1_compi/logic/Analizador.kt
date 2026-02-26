package com.example.practica1_compi.logic

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

            val resultadoParse = parser.parse()

            val programa = resultadoParse.value as? NodoPrograma

            val erroresTotales = lexer.errorReportes + parser.errorReportes

            if (erroresTotales.isNotEmpty() || programa == null) {
                return ResultadoAnalisis(errores = erroresTotales)
            }

            val operadoresTotales = lexer.operadores
            val extractor = ExtractorReporteEstructuras()
            val estructurasTotales = extractor.extraer(programa)

            //constructor de ocurrencias
            val lineas = texto.lines()
            val operadoresConOcurrencia = operadoresTotales.map { operadorActual ->
                val ocurrenciaDetallada = ConstructorOcurrencia.construirOcurrencia(lineas, operadorActual.linea, operadorActual.columna)
                operadorActual.copy(ocurrencia = ocurrenciaDetallada)
            }

            return ResultadoAnalisis(errores = erroresTotales, operadores = operadoresConOcurrencia, estructuras = estructurasTotales)

        } catch (e: Exception) {
            return ResultadoAnalisis(
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