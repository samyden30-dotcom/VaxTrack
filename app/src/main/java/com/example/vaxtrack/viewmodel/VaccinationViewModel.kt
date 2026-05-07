package com.example.vaxtrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaxtrack.data.model.Menage
import com.example.vaxtrack.data.repository.MenageRepository
import com.example.vaxtrack.data.repository.PhotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VaccinationViewModel(
    private val menageRepository: MenageRepository? = null,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _menages = MutableStateFlow<List<Menage>>(emptyList())
    val menages: StateFlow<List<Menage>> = _menages

    private val _totalDoses = MutableStateFlow(0)
    val totalDoses: StateFlow<Int> = _totalDoses

    init {
        loadMenages()
    }

    /**
     * Upload une photo et retourne l’URL publique.
     */
    fun uploadMenagePhoto(bytes: ByteArray, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val url = photoRepository.uploadPhoto(bytes)
                onResult(url)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    /**
     * Ajoute un ménage.
     */
    fun ajouterMenage(menage: Menage) {
        viewModelScope.launch {
            menageRepository?.addMenage(menage)
            loadMenages()
        }
    }

    /**
     * Charge les ménages.
     */
    fun loadMenages() {
        viewModelScope.launch {
            val data = menageRepository?.getMenages() ?: emptyList()
            _menages.value = data
            _totalDoses.value = data.sumOf { it.nbDoses }
        }
    }
}
