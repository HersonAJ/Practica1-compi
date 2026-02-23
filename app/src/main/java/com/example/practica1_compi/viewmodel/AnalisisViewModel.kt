package com.example.practica1_compi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppUIState(
    val codigo: String = "",
    val analisisState: AnalisisState = AnalisisState.Inicial,
    val errores: List<String> = emptyList(),
    val tokens: List<String> = emptyList()
)

enum class AnalisisState{
    Inicial, Exitoso, ConErrores
}

class AnalisisViewModel: ViewModel() {

    //estado privado mutable
    private val _uiState = MutableStateFlow(AppUIState())

    //estado publico inmutable para exponer la ui
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    //getter para el codigo
    val codigo: String
        get() = _uiState.value.codigo

    //actualicar el codigo en el estado
    fun actualizarCodigo(nuevoCodigo: String) {
        _uiState.value = _uiState.value.copy(
            codigo = nuevoCodigo
        )
    }

    //funcion temporal para analizar el codigo
    fun analizarCodigo() {
        viewModelScope.launch {

            if (_uiState.value.codigo.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    analisisState = AnalisisState.ConErrores,
                    errores = listOf("Errores: el codigo no puede estar vacio")
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    analisisState = AnalisisState.Exitoso,
                    errores = emptyList()
                )
            }
        }
    }

    //funcion para limpiar
    fun limpiar() {
        _uiState.value = AppUIState()
    }
}