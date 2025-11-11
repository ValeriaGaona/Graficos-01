package com.vgg.meditationtracker.data.database

import android.content.Context
import androidx.room.*
import androidx.room.TypeConverter

@Database(entities = [MeditationSession::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MeditationDatabase : RoomDatabase() {
    abstract fun meditationDao(): MeditationDao

    companion object {
        @Volatile
        private var INSTANCE: MeditationDatabase? = null

        fun getDatabase(context: Context): MeditationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MeditationDatabase::class.java,
                    "meditation_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromSessionType(type: SessionType): String = type.name

    @TypeConverter
    fun toSessionType(value: String): SessionType = SessionType.valueOf(value)
}
