package com.example.vaxtrack.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "agents")
@Serializable
data class Agent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val email: String,
    val totalDoses: Int = 0
)
