package com.example.vaxtrack.data.repository

import com.example.vaxtrack.data.local.SyncDao
import com.example.vaxtrack.data.local.SyncOperation
import com.example.vaxtrack.remote.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import com.example.vaxtrack.data.model.Menage
import com.example.vaxtrack.data.model.Agent

class SyncRepository(private val syncDao: SyncDao) {
    private val supabase = SupabaseClient.client

    suspend fun addOperation(operation: SyncOperation) {
        syncDao.insertOperation(operation)
    }

    suspend fun processPendingOperations() = withContext(Dispatchers.IO) {
        val operations = syncDao.getAllOperations()
        for (op in operations) {
            try {
                when (op.entity) {
                    "Menage" -> {
                        val menage = Json.decodeFromString<Menage>(op.payload)
                        if (op.type == "INSERT") {
                            supabase.from("menages").insert(menage)
                        } else if (op.type == "DELETE") {
                            supabase.from("menages").delete {
                                filter {
                                    eq("id", menage.id)
                                }
                            }
                        }
                    }
                    "Agent" -> {
                        val agent = Json.decodeFromString<Agent>(op.payload)
                        if (op.type == "INSERT") {
                            supabase.from("agents").insert(agent)
                        } else if (op.type == "DELETE") {
                            supabase.from("agents").delete {
                                filter {
                                    eq("id", agent.id)
                                }
                            }
                        }
                    }
                }
                // Si succès → supprimer de la queue
                syncDao.deleteOperation(op.id)
            } catch (e: Exception) {
                // Si échec → garder dans la queue pour réessayer plus tard
            }
        }
    }
}
