package com.vgg.meditationtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgg.meditationtracker.data.database.MeditationSession
import com.vgg.meditationtracker.data.database.SessionType
import com.vgg.meditationtracker.data.repository.MeditationRepository
import com.vgg.meditationtracker.data.repository.SessionStatistics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted

class MeditationViewModel(
    private val repository: MeditationRepository
) : ViewModel() {

    val allSessions = repository.allSessions.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val weeklySessions = repository.getWeeklySessions().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pieChartData = repository.getPieChartData(30).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val barChartData = repository.getBarChartData(7).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _statistics = MutableStateFlow<SessionStatistics?>(null)
    val statistics: StateFlow<SessionStatistics?> = _statistics.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    init {
        calculateStatistics()
    }

    fun insertSession(type: SessionType, duration: Int, mood: Int, notes: String = "") {
        if (duration <= 0) {
            _message.value = "La duración debe ser mayor a 0"
            return
        }
        if (mood !in 1..5) {
            _message.value = "El estado de ánimo debe estar entre 1 y 5"
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val session = MeditationSession(type = type, durationMinutes = duration, mood = mood, notes = notes)
                repository.insertSession(session)
                _message.value = "✓ Sesión guardada exitosamente"
                calculateStatistics()
            } catch (e: Exception) {
                _message.value = "Error al guardar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteSession(session: MeditationSession) {
        viewModelScope.launch {
            try {
                repository.deleteSession(session)
                _message.value = "Sesión eliminada"
                calculateStatistics()
            } catch (e: Exception) {
                _message.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    private fun calculateStatistics() {
        viewModelScope.launch {
            weeklySessions.value.let { sessions ->
                if (sessions.isNotEmpty()) {
                    _statistics.value = repository.getStatistics(sessions)
                }
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }

    fun getSessionsByType(type: SessionType) = repository.getSessionsByType(type)
}
