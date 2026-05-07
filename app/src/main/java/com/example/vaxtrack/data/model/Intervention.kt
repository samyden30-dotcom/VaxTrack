package com.example.vaxtrack.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Intervention(
    val id: Int? = null,
    val menageId: Int,
    val agentId: Int,
    val nbPersonnesVaccinees: Int,
    val nbDosesAdministrees: Int,
    val latitude: Double,
    val longitude: Double,
    val photoUrl: String? = null,
    val dateIntervention: String = "" // Simplifié pour la sérialisation
)
