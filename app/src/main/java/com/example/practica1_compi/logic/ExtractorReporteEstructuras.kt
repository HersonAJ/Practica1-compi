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

}
