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

        btnAnalizar.setOnClickListener {
            val entrada = inputText.text.toString()
            probarLexer(entrada)
        }
    }

    private fun probarLexer(entrada: String) {
        try {
            val reader = StringReader(entrada)
            val lexer = Lexer(reader)

            var token: Token? = lexer.yylex()
            var contador = 0

            while (token != null) {
                contador++

                Log.d(
                    "MI_LEXER",
                    "Token $contador: tipo=${token.tipo}, " +
                            "lexema='${token.lexema}', linea=${token.linea}, " +
                            "columna=${token.columna}"
                )

                token = lexer.yylex()
            }

            Log.d("MI_LEXER", "Total de tokens: $contador")

        } catch (e: Exception) {
            Log.e("MI_LEXER", "Error: ${e.message}", e)
        }
    }
}
