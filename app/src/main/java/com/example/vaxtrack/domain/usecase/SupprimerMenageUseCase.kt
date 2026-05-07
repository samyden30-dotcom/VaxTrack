package com.example.vaxtrack.domain.usecase

import com.example.vaxtrack.data.repository.VaccinationRepository

class SupprimerMenageUseCase(private val repository: VaccinationRepository) {
    suspend operator fun invoke(id: Int) {
        repository.deleteMenage(id)
    }
}
