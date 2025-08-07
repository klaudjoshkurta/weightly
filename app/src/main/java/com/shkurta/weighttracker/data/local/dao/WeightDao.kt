package com.shkurta.weighttracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.shkurta.weighttracker.data.local.entity.WeightEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<WeightEntry>>

    @Insert
    suspend fun insertEntry(entry: WeightEntry)

    @Delete
    suspend fun deleteEntry(entry: WeightEntry)
}