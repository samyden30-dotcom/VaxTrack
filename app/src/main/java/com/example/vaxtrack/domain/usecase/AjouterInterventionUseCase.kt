package com.example.vaxtrack.domain.usecase

import com.example.vaxtrack.data.model.Intervention
import com.example.vaxtrack.data.repository.InterventionRepository

class AjouterInterventionUseCase(private val repository: InterventionRepository) {
    suspend operator fun invoke(intervention: Intervention) {
        repository.addIntervention(intervention)
    }
}
