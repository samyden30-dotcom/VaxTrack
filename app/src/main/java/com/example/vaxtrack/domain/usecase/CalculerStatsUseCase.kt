package com.example.vaxtrack.domain.usecase

import com.example.vaxtrack.data.repository.VaccinationRepository
import com.example.vaxtrack.data.model.Menage

class CalculerStatsUseCase(private val repository: VaccinationRepository) {
    suspend operator fun invoke(): Int {
        val menages: List<Menage> = repository.getMenages()
        return menages.sumOf { it.nbDoses }
    }
}
