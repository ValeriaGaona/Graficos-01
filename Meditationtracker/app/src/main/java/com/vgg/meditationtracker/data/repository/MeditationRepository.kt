package com.vgg.meditationtracker.data.repository

import com.vgg.meditationtracker.data.database.*
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class MeditationRepository(private val dao: MeditationDao) {

    val allSessions: Flow<List<MeditationSession>> = dao.getAllSessions()

    suspend fun insertSession(session: MeditationSession): Long = dao.insertSession(session)

    fun getWeeklySessions(): Flow<List<MeditationSession>> {
        val weekAgo = getDateDaysAgo(7)
        return dao.getSessionsSince(weekAgo)
    }

    fun getMonthlySessions(): Flow<List<MeditationSession>> {
        val monthAgo = getDateDaysAgo(30)
        return dao.getSessionsSince(monthAgo)
    }

    fun getPieChartData(days: Int = 30) = dao.getTotalMinutesByType(getDateDaysAgo(days))
    fun getBarChartData(days: Int = 7) = dao.getDailyMinutes(getDateDaysAgo(days))

    suspend fun deleteSession(session: MeditationSession) = dao.deleteSession(session)
    suspend fun updateSession(session: MeditationSession) = dao.updateSession(session)
    fun getSessionsByType(type: SessionType): Flow<List<MeditationSession>> = dao.getSessionsByType(type)

    suspend fun getStatistics(sessions: List<MeditationSession>): SessionStatistics {
        return SessionStatistics(
            totalSessions = sessions.size,
            totalMinutes = sessions.sumOf { it.durationMinutes },
            averageMinutes = if (sessions.isNotEmpty()) sessions.sumOf { it.durationMinutes } / sessions.size else 0,
            averageMood = if (sessions.isNotEmpty()) sessions.sumOf { it.mood }.toFloat() / sessions.size else 0f,
            mostFrequentType = sessions.groupBy { it.type }.maxByOrNull { it.value.size }?.key ?: SessionType.MEDITATION
        )
    }

    private fun getDateDaysAgo(days: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}

data class SessionStatistics(
    val totalSessions: Int,
    val totalMinutes: Int,
    val averageMinutes: Int,
    val averageMood: Float,
    val mostFrequentType: SessionType
)
