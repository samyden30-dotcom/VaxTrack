package com.example.vaxtrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vaxtrack.data.model.Menage

@Dao
interface MenageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenage(menage: Menage)

    @Query("SELECT * FROM menages")
    suspend fun getAllMenages(): List<Menage>

    @Query("DELETE FROM menages WHERE id = :menageId")
    suspend fun deleteMenage(menageId: Int)
}
