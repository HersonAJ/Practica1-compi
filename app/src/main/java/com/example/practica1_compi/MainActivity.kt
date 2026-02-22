package com.example.practica1_compi

import com.example.practica1_compi.R
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.practica1_compi.models.Token
import android.widget.TextView
import java.io.StringReader
import com.example.practica1_compi.analizadores.Lexer
import com.example.practica1_compi.analizadores.Parser
import java.lang.Exception

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val btnAnalizar = findViewById<Button>(R.id.btnAnalizar)
        val outputText = findViewById<TextView>(R.id.outputText)

        btnAnalizar.setOnClickListener {
            val entrada = inputText.text.toString()
            val resultados = analizarEntrada(entrada)
            outputText.text = resultados
        }
    }

    private fun analizarEntrada(entrada: String): String {
        val resultados = StringBuilder();
        var parser: Parser? = null

        try {
            resultados.append("---analisis lexico-----\n\n")

            val reader = StringReader(entrada)
            val lexer = Lexer(reader)

            resultados.append("tokens encontrados: ")
            resultados.append("---------------------")

            var contador = 0
            var simbolo = lexer.next_token()

            while (simbolo != null && simbolo.sym != 0 ) {
                contador++

                val tipo = obtenerNombreToken(simbolo.sym)
                val lexema = simbolo.value?.toString() ?: ""
                val linea = simbolo.left ?: 0
                val columna = simbolo.right ?: 0

                resultados.append("Token #$contador\n")
                resultados.append(" Tipo: $tipo (${simbolo.sym})\n")
                resultados.append(" Lexema: \"$lexema\"\n")
                resultados.append(" linea: $linea, Columna: $columna\n\n")

                simbolo = lexer.next_token()
            }

            resultados.append("Total de tokens: $contador\n\n")

            //errores lexicos

            val erroresLexicos = lexer.getLexicalErrors()

            if (erroresLexicos.isNotEmpty()) {
                resultados.append("----errores lexicos------\n")
                erroresLexicos.forEach { error ->
                    resultados.append(" * $error\n")
                }
                resultados.append("\n")
            }

            if (erroresLexicos.isEmpty()) {
                resultados.append("------analisis sintactico--------\n\n")

                try {
                    val parserReader = StringReader(entrada)
                    val parserLexer = Lexer(parserReader)
                    parser = Parser(parserLexer)

                    //analisis sintactico
                    val resultado = parser.parse()

                    //verificar errores sintacticos

                    val erroresSintacticos = parser.getSyntaxErrors()
                    if (erroresSintacticos.isNotEmpty()) {
                        resultados.append("Se encontraron errores sintacticos: \n")
                        erroresSintacticos.forEach { error ->
                        resultados.append("* $error \n")
                        }
                        resultados.append("\n")
                        resultados.append("no se puede generar el diagrama - hay errores sintacticos")
                    } else {
                        resultados.append("Analisis sintactico exitoso- sin errores\n")
                        resultados.append("se puede generar el diagrama\n")
                    }
                } catch (e: Exception) {
                    resultados.append("error durante el analisis sintactico:  ${e.message}\n")
                    e.printStackTrace()
                }
            } else {
                resultados.append("no se puede realizar el analisis sintactico, hay errores lexicos\n")
            }

            resultados.append("------resumen-------\n")

            when {
                erroresLexicos.isNotEmpty() -> {
                    resultados.append("compilacion fallida: errores lexicos detectados\n")
                }
                parser?.getSyntaxErrors()?.isNotEmpty() == true -> {
                    resultados.append("compilacion fallida: errores sintacticos detectados\n")
                }
                else -> {
                    resultados.append("compilacion exitosa: no se detectaron errores\n")
                }
            }
        } catch (e: kotlin.Exception) {
            resultados.append("Error inesperado: ${e.message}\n")
            e.printStackTrace()
        }
        return resultados.toString()
    }

    private fun obtenerNombreToken(id: Int): String {
        return when (id) {
            // Palabras reservadas pseudocódigo
            com.example.practica1_compi.analizadores.sym.INICIO -> "INICIO"
            com.example.practica1_compi.analizadores.sym.FIN -> "FIN"
            com.example.practica1_compi.analizadores.sym.VAR -> "VAR"
            com.example.practica1_compi.analizadores.sym.SI -> "SI"
            com.example.practica1_compi.analizadores.sym.ENTONCES -> "ENTONCES"
            com.example.practica1_compi.analizadores.sym.FINSI -> "FINSI"
            com.example.practica1_compi.analizadores.sym.MIENTRAS -> "MIENTRAS"
            com.example.practica1_compi.analizadores.sym.HACER -> "HACER"
            com.example.practica1_compi.analizadores.sym.FINMIENTRAS -> "FINMIENTRAS"
            com.example.practica1_compi.analizadores.sym.MOSTRAR -> "MOSTRAR"
            com.example.practica1_compi.analizadores.sym.LEER -> "LEER"

            // Operadores
            com.example.practica1_compi.analizadores.sym.OPERADOR_ASIGNACION -> "ASIGNACION"
            com.example.practica1_compi.analizadores.sym.SUMA -> "SUMA"
            com.example.practica1_compi.analizadores.sym.RESTA -> "RESTA"
            com.example.practica1_compi.analizadores.sym.MULT -> "MULTIPLICACION"
            com.example.practica1_compi.analizadores.sym.DIV -> "DIVISION"
            com.example.practica1_compi.analizadores.sym.MENOR -> "MENOR"
            com.example.practica1_compi.analizadores.sym.MAYOR -> "MAYOR"
            com.example.practica1_compi.analizadores.sym.MENOR_IGUAL -> "MENOR_IGUAL"
            com.example.practica1_compi.analizadores.sym.MAYOR_IGUAL -> "MAYOR_IGUAL"
            com.example.practica1_compi.analizadores.sym.IGUALDAD -> "IGUALDAD"
            com.example.practica1_compi.analizadores.sym.DISTINTO -> "DISTINTO"
            com.example.practica1_compi.analizadores.sym.AND -> "AND"
            com.example.practica1_compi.analizadores.sym.OR -> "OR"
            com.example.practica1_compi.analizadores.sym.NOT_LOGICO -> "NOT"

            // Literales e identificadores
            com.example.practica1_compi.analizadores.sym.IDENTIFICADOR -> "IDENTIFICADOR"
            com.example.practica1_compi.analizadores.sym.NUMERO -> "NUMERO"
            com.example.practica1_compi.analizadores.sym.CADENA -> "CADENA"

            // Configuración
            com.example.practica1_compi.analizadores.sym.SEPARADOR_SECCION -> "SEPARADOR"
            com.example.practica1_compi.analizadores.sym.CONFIG -> "CONFIG"
            com.example.practica1_compi.analizadores.sym.FIGURA -> "FIGURA"
            com.example.practica1_compi.analizadores.sym.LETRA -> "LETRA"
            com.example.practica1_compi.analizadores.sym.COLOR_HEX -> "COLOR_HEX"

            // Símbolos
            com.example.practica1_compi.analizadores.sym.COMA -> "COMA"
            com.example.practica1_compi.analizadores.sym.PIPE -> "PIPE"
            com.example.practica1_compi.analizadores.sym.LPAREN -> "PARENTESIS_IZQ"
            com.example.practica1_compi.analizadores.sym.RPAREN -> "PARENTESIS_DER"

            // Tokens de error
            com.example.practica1_compi.analizadores.sym.ERROR -> "ERROR_GENERAL"
            com.example.practica1_compi.analizadores.sym.ERROR_IDENTIFICADOR_INVALIDO -> "ERROR_IDENTIFICADOR"
            com.example.practica1_compi.analizadores.sym.ERROR_DECIMAL_INVALIDO -> "ERROR_DECIMAL"

            // EOF
            0 -> "EOF"
            else -> "DESCONOCIDO($id)"
        }
    }

}
