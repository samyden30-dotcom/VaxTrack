package com.example.vaxtrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_operations")
data class SyncOperation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "INSERT", "UPDATE", "DELETE"
    val entity: String, // "Menage", "Agent"
    val payload: String, // JSON payload
    val timestamp: Long = System.currentTimeMillis()
)
