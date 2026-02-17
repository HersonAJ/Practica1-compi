package com.example.practica1_compi

import Lexer
import com.example.practica1_compi.R
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.practica1_compi.models.Token
import android.widget.TextView
import java.io.StringReader

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

            var token: Token? = lexer.yylex()
            var contador = 0

            resultados.append("analisis lexico\n")
            resultados.append("------------------------------\n")

            while (token != null) {
                contador++

                resultados.append("Token $contador: \n")
                resultados.append(" Tipo: ${token.tipo}\n")
                resultados.append(" Lexema:${token.lexema}\n")
                resultados.append(" linea: ${token.linea}\n")
                resultados.append(" columna: ${token.columna}\n\n")

                token = lexer.yylex()
            }
            resultados.append("------------------------------\n")
            resultados.append("Total de tokens: $contador")

        } catch (e: Exception) {
            resultados.append("Error:  ${e.message}")
        }
        return resultados.toString()
    }
}
