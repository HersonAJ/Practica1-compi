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
import com.example.practica1_compi.analizadores.Lexer;

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val btnAnalizar = findViewById<Button>(R.id.btnAnalizar)
        val outputText = findViewById<TextView>(R.id.outputText)

        btnAnalizar.setOnClickListener {
            val entrada = inputText.text.toString()
            val resultados = probarLexer(entrada)
            outputText.text = resultados
        }
    }

    private fun probarLexer(entrada: String): String {

        val resultados = StringBuilder()

        try {
            val reader = StringReader(entrada)
            val lexer = Lexer(reader)

            resultados.append("Analisis lexico: \n")
            resultados.append("-----------------\n\n")

            var contador = 0
            var simbolo = lexer.next_token()

            while (simbolo != null && simbolo.sym != 0) {
                contador++;

                val tipo = obtenerNombreToken(simbolo.sym)
                val lexema = simbolo.value?.toString() ?: ""
                val linea = simbolo.left ?: 0
                val columna = simbolo.right ?: 0

                resultados.append("Token #$contador \n")
                resultados.append(" Tipo: $tipo (${simbolo.sym})\n")
                resultados.append(" Lexema: \"$lexema\"\n")
                resultados.append((" Linea: $linea, Columna: $columna\n\n"))

                simbolo = lexer.next_token()
            }

            resultados.append("---------------\n")
            resultados.append("Total de tokens: $contador\n")

            if (contador == 0) {
                resultados.append("No se encontraron tokens, entrada vacia")
            }
        } catch (e: Exception) {
            resultados.append("Error:  ${e.message}")
        }
        return resultados.toString()
    }

    private fun obtenerNombreToken(id: Int): String {
        return when (id) {
            //pseudocodigo

            com.example.practica1_compi.analizadores.sym.IDENTIFICADOR -> "IDENTIFICADOR"
            com.example.practica1_compi.analizadores.sym.NUMERO -> "NUMERO"
            com.example.practica1_compi.analizadores.sym.CADENA -> "CADENA"
            com.example.practica1_compi.analizadores.sym.NOT_LOGICO -> "NOT_LOGICO"
            com.example.practica1_compi.analizadores.sym.OPERADOR_ASIGNACION -> "ASIGNACION"

            //configuracion
            com.example.practica1_compi.analizadores.sym.SEPARADOR_SECCION -> "SEPARADOR_SECCION"
            com.example.practica1_compi.analizadores.sym.CONFIG -> "CONFIG"
            com.example.practica1_compi.analizadores.sym.FIGURA -> "FIGURA"
            com.example.practica1_compi.analizadores.sym.LETRA -> "LETRA"
            com.example.practica1_compi.analizadores.sym.COLOR_HEX -> "COLOR_HEX"

            //simbolos
            com.example.practica1_compi.analizadores.sym.COMA -> "COMA"
            com.example.practica1_compi.analizadores.sym.PIPE -> "PIPE"
            com.example.practica1_compi.analizadores.sym.LPAREN -> "LPAREN"
            com.example.practica1_compi.analizadores.sym.RPAREN -> "RPAREN"

            //errores
            com.example.practica1_compi.analizadores.sym.ERROR -> "ERROR_GENERAL"
            com.example.practica1_compi.analizadores.sym.ERROR_IDENTIFICADOR_INVALIDO -> "ERROR_IDENTIFICADOR"
            com.example.practica1_compi.analizadores.sym.ERROR_DECIMAL_INVALIDO -> "ERROR_DECIMAL"

            // EOF
            0 -> "EOF"
            else -> "DESCONOCIDO($id)"
        }
    }

}
