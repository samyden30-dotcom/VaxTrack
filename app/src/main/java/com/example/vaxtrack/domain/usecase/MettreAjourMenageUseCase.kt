package com.example.vaxtrack.domain.usecase

import com.example.vaxtrack.data.repository.VaccinationRepository

class MettreAJourMenageUseCase(private val repository: VaccinationRepository) {
    suspend operator fun invoke(id: Int, updates: Map<String, Any>) {
        repository.updateMenage(id, updates)
    }
}
