package com.example.vaxtrack.data.repository

import com.example.vaxtrack.data.local.AgentDao
import com.example.vaxtrack.data.model.Agent
import com.example.vaxtrack.remote.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AgentRepository(
    private val agentDao: AgentDao
) {
    private val supabase = SupabaseClient.client

    suspend fun addAgent(agent: Agent) = withContext(Dispatchers.IO) {
        agentDao.insertAgent(agent)
        try {
            supabase.from("agents").insert(agent)
        } catch (_: Exception) {}
    }

    suspend fun getAgents(): List<Agent> = withContext(Dispatchers.IO) {
        val local = agentDao.getAllAgents()
        if (local.isNotEmpty()) return@withContext local
        return@withContext try {
            val response = supabase.from("agents").select()
            val agents: List<Agent> = response.decodeList<Agent>()
            for (agent in agents) {
                agentDao.insertAgent(agent)
            }
            agents
        } catch (_: Exception) {
            emptyList()
        }
    }
}
