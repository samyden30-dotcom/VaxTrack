package com.example.vaxtrack.data.repository

import com.example.vaxtrack.data.local.MenageDao
import com.example.vaxtrack.data.model.Menage
import com.example.vaxtrack.remote.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenageRepository(
    private val menageDao: MenageDao
) {
    private val supabase = SupabaseClient.client

    /**
     * Ajoute un ménage en local et tente de synchroniser avec Supabase.
     */
    suspend fun addMenage(menage: Menage) = withContext(Dispatchers.IO) {
        // Sauvegarde locale
        menageDao.insertMenage(menage)

        // Tentative de synchro online
        try {
            supabase.from("menages").insert(menage)
        } catch (e: Exception) {
            // En cas d’échec réseau, les données restent en local
        }
    }

    /**
     * Récupère tous les ménages (offline-first).
     */
    suspend fun getMenages(): List<Menage> = withContext(Dispatchers.IO) {
        val localData = menageDao.getAllMenages()
        if (localData.isNotEmpty()) {
            return@withContext localData
        }
        // Si vide en local, tenter Supabase
        return@withContext try {
            val response = supabase.from("menages").select()
            val menages = response.decodeList<Menage>()
            // Mettre en cache local
            for (menage in menages) {
                menageDao.insertMenage(menage)
            }
            menages
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Supprime un ménage en local et sur Supabase.
     */
    suspend fun deleteMenage(menageId: Int) = withContext(Dispatchers.IO) {
        menageDao.deleteMenage(menageId)
        try {
            supabase.from("menages").delete {
                filter {
                    eq("id", menageId)
                }
            }
        } catch (e: Exception) {
            // En cas d’échec, suppression locale seulement
        }
    }
}
