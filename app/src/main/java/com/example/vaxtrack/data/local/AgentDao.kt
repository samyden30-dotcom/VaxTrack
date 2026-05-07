package com.example.vaxtrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vaxtrack.data.model.Agent

@Dao
interface AgentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgent(agent: Agent)

    @Query("SELECT * FROM agents")
    suspend fun getAllAgents(): List<Agent>

    @Query("DELETE FROM agents WHERE id = :agentId")
    suspend fun deleteAgent(agentId: Int)
}
