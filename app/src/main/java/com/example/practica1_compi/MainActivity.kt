package com.example.practica1_compi

import Lexer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.practica1_compi.models.Token
import java.io.StringReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val entrada = """
            #este es un comentario
            INICIO
            VAR x = 10
            VAR y = 20.5
            SI (x < y) ENTONCES
                MOSTRAR "Hola Kotlin"
            FINSI
            FIN
            %%%%
            %DEFAULT=1
            %COLOR_TEXTO_SI=HFF5733|1
        """.trimIndent()

        probarLexer(entrada)
    }

    private fun probarLexer(entrada: String) {
        try {
            val reader = StringReader(entrada)
            val lexer = Lexer(reader)

            var token: Token? = lexer.yylex()
            var contador = 0

            while (token != null) {
                contador++
                // Cambia println por Log.d
                Log.d("MI_LEXER", "Token $contador: tipo=${token.tipo}, " +
                        "lexema='${token.lexema}', linea=${token.linea}, " +
                        "columna=${token.columna}")
                token = lexer.yylex()
            }

            Log.d("MI_LEXER", "Total de tokens: $contador")

        } catch (e: Exception) {
            Log.e("MI_LEXER", "Error: ${e.message}", e)
        }
    }
}
