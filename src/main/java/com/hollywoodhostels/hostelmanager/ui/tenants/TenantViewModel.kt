package com.hollywoodhostels.hostelmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TenantViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<TenantUiState>(TenantUiState.Loading)
    val uiState: StateFlow<TenantUiState> = _uiState

    init {
        loadTenants()
    }

    private fun loadTenants() {
        viewModelScope.launch {
            // Load tenants from repository
            _uiState.value = TenantUiState.Success(emptyList())
        }
    }
}

sealed class TenantUiState {
    object Loading : TenantUiState()
    data class Success(val tenants: List<Any>) : TenantUiState()
    data class Error(val message: String) : TenantUiState()
}