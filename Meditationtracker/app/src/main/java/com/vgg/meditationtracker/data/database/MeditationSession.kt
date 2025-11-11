package com.vgg.meditationtracker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meditation_sessions")
data class MeditationSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: SessionType,
    val durationMinutes: Int,
    val date: Long = System.currentTimeMillis(),
    val mood: Int,
    val notes: String = ""
)

enum class SessionType {
    MEDITATION,
    BREATHING,
    YOGA,
    JOURNALING,
    GRATITUDE
}
