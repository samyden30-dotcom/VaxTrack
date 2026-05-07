package com.example.vaxtrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SyncDao {
    @Insert
    suspend fun insertOperation(operation: SyncOperation)

    @Query("SELECT * FROM sync_operations ORDER BY timestamp ASC")
    suspend fun getAllOperations(): List<SyncOperation>

    @Query("DELETE FROM sync_operations WHERE id = :operationId")
    suspend fun deleteOperation(operationId: Int)
}
