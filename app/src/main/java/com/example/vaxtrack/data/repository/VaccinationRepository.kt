package com.example.vaxtrack.data.repository

import com.example.vaxtrack.data.model.Menage
import io.github.jan.supabase.postgrest.Postgrest

class VaccinationRepository(private val client: Postgrest) {

    // Lire tous les ménages
    suspend fun getMenages(): List<Menage> {
        return client
            .from("campagne_vaccination")
            .select()
            .decodeList<Menage>()
    }

    // Créer un nouveau ménage
    suspend fun addMenage(menage: Menage) {
        client.from("campagne_vaccination").insert(menage)
    }

    // Mettre à jour un ménage existant
    suspend fun updateMenage(id: Int, updates: Map<String, Any>) {
        client.from("campagne_vaccination")
            .update(updates) {
                filter {
                    eq("id", id)
                }
            }
    }

    // Supprimer un ménage
    suspend fun deleteMenage(id: Int) {
        client.from("campagne_vaccination")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}
