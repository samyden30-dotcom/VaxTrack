package com.example.vaxtrack.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "menages")
@Serializable
data class Menage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chefMenage: String,
    val contact: String,
    val nbPersonnes: Int,
    val nbDoses: Int,
    val latitude: Double,
    val longitude: Double,
    val photoUrl: String? = null,
    val status: String = "En cours", // ou "Couverte"
    val createdAt: String? = null
)
