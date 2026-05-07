package com.example.vaxtrack.data.repository

import com.example.vaxtrack.data.model.Intervention
import io.github.jan.supabase.postgrest.Postgrest

class InterventionRepository(private val client: Postgrest) {

    // Lire toutes les interventions
    suspend fun getInterventions(): List<Intervention> {
        return client
            .from("interventions")
            .select()
            .decodeList<Intervention>()
    }

    // Ajouter une intervention
    suspend fun addIntervention(intervention: Intervention) {
        client.from("interventions").insert(intervention)
    }

    // Mettre à jour une intervention
    suspend fun updateIntervention(id: Int, updates: Map<String, Any>) {
        client.from("interventions")
            .update(updates) {
                filter {
                    eq("id", id)
                }
            }
    }

    // Supprimer une intervention
    suspend fun deleteIntervention(id: Int) {
        client.from("interventions")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}
