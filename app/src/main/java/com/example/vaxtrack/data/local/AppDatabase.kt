package com.example.vaxtrack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vaxtrack.data.model.Agent
import com.example.vaxtrack.data.model.Menage

@Database(
    entities = [Agent::class, Menage::class, SyncOperation::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agentDao(): AgentDao
    abstract fun menageDao(): MenageDao
    abstract fun syncDao(): SyncDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vaxtrack_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
