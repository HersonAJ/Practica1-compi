package com.example.practica1_compi.logic

import com.example.practica1_compi.models.*

class ExtractorReporteEstructuras {

        fun extraer(programa: NodoPrograma): List<ReporteEstructura> {
            val estructuras = mutableListOf<ReporteEstructura>()
            for (nodo in programa.pseudocodigo) {
                when (nodo) {
                    is NodoSi -> estructuras.add(
                        ReporteEstructura("SI", nodo.linea, nodo.condicion.aTexto())
                    )
                    is NodoMientras -> estructuras.add(
                        ReporteEstructura("MIENTRAS", nodo.linea, nodo.condicion.aTexto())
                    )
                    else -> {}
                }
            }
            return estructuras
        }


    private fun recorrerNodos(nodos: List<NodoASTBase>, reportes: MutableList<ReporteEstructura>) {
        for (nodo in nodos) {
            when (nodo) {
                is NodoSi -> {
                    reportes.add(
                        ReporteEstructura(
                            tipo = "SI",
                            linea = nodo.linea,
                            condicion = nodo.condicion.textoCodigo()
                        )
                    )
                    recorrerNodos(nodo.bloque, reportes)
                }
                is NodoMientras -> {
                    reportes.add(
                        ReporteEstructura(
                            tipo = "MIENTRAS",
                            linea = nodo.linea,
                            condicion = nodo.condicion.textoCodigo()
                        )
                    )
                    recorrerNodos(nodo.bloque, reportes)
                }
                is NodoBloque -> {
                    recorrerNodos(nodo.instrucciones, reportes)
                }
                else -> {
                    // otros nodos no generan reporte de estructuras
                }
            }
        }
    }
}
