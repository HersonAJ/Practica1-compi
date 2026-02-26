package com.example.practica1_compi.models

//Clase base sellada
sealed class NodoASTBase {
    abstract val linea: Int
    abstract val columna: Int

    open fun textoCodigo(): String = this.toString()
    open fun aTexto(): String = this.toString()
}

data class NodoDeclaracion(
    override val linea: Int,
    override val columna: Int,
    val nombre: String,
    val valor: NodoASTBase?
) : NodoASTBase()

data class NodoAsignacion(
    override val linea: Int,
    override val columna: Int,
    val nombre: String,
    val expresion: NodoASTBase
) : NodoASTBase()

data class NodoMostrar(
    override val linea: Int,
    override val columna: Int,
    val contenido: NodoASTBase
) : NodoASTBase()

data class NodoLeer(
    override val linea: Int,
    override val columna: Int,
    val nombre: String
) : NodoASTBase()

data class NodoSi(
    override val linea: Int,
    override val columna: Int,
    val condicion: NodoASTBase,
    val bloque: List<NodoASTBase>
) : NodoASTBase()

data class NodoMientras(
    override val linea: Int,
    override val columna: Int,
    val condicion: NodoASTBase,
    val bloque: List<NodoASTBase>
) : NodoASTBase()

data class NodoBloque(
    override val linea: Int,
    override val columna: Int,
    val instrucciones: List<NodoASTBase>
) : NodoASTBase()


data class NodoExpresion(
    override val linea: Int,
    override val columna: Int,
    val operador: String,
    val izquierda: NodoASTBase,
    val derecha: NodoASTBase
) : NodoASTBase() {
    override fun textoCodigo(): String =
        "(${izquierda.textoCodigo()} $operador ${derecha.textoCodigo()})"

    fun evaluar(): Double {
        val li = (izquierda as? NodoLiteral)?.evaluar()
            ?: (izquierda as? NodoExpresion)?.evaluar()
            ?: throw IllegalArgumentException("Expresion invalida")

        val ld = (derecha as? NodoLiteral)?.evaluar()
            ?: (derecha as? NodoExpresion)?.evaluar()
            ?: throw IllegalArgumentException("Expresion invalida")

        return when ( operador) {
            "+" -> li + ld
            "-" -> li - ld
            "*" -> li * ld
            "/" -> li / ld
            else -> throw IllegalArgumentException("Operador desconocido: $operador")
        }
    }

    override fun aTexto(): String = "(${izquierda.aTexto()} $operador ${derecha.aTexto()})"
}


data class NodoCondicion(
    override val linea: Int,
    override val columna: Int,
    val operador: String,
    val izquierda: NodoASTBase,
    val derecha: NodoASTBase?
) : NodoASTBase() {
     override fun aTexto(): String {
        val izq = izquierda.aTexto()
        val der = derecha?.aTexto() ?: ""
        return "$izq $operador $der"
    }
}

data class NodoConfiguracion(
    override val linea: Int,
    override val columna: Int,
    val instruccion: String,
    val valor: String,
    val indice: Int? = null
) : NodoASTBase()

data class NodoPrograma(
    override val linea: Int,
    override val columna: Int,
    val pseudocodigo: List<NodoASTBase>,
    val configuraciones: List<NodoConfiguracion>
) : NodoASTBase()

data class NodoLiteral(
    override val linea: Int,
    override val columna: Int,
    val valor: String
) : NodoASTBase() {
    override fun textoCodigo(): String = valor
    fun evaluar(): Double = valor.toDouble()
    override fun aTexto(): String = valor
}

data class NodoVariable(
    override val linea: Int,
    override val columna: Int,
    val nombre: String
) : NodoASTBase() {
    override fun textoCodigo(): String = nombre
    override fun aTexto(): String = nombre
}
