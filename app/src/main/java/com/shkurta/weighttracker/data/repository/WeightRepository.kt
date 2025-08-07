package com.shkurta.weighttracker.data.repository

import com.shkurta.weighttracker.data.local.dao.WeightDao
import com.shkurta.weighttracker.data.local.entity.WeightEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeightRepository {
    fun weightHistory(): Flow<List<WeightEntry>>
    suspend fun addWeight(value: Float)
    suspend fun deleteWeight(entry: WeightEntry)
}

class WeightRepositoryImpl @Inject constructor(
    private val weightDao: WeightDao
) : WeightRepository {
    override fun weightHistory(): Flow<List<WeightEntry>> = weightDao.getAllEntries()
    override suspend fun addWeight(value: Float) {
        weightDao.insertEntry(WeightEntry(weight = value))
    }
    override suspend fun deleteWeight(entry: WeightEntry) {
        weightDao.deleteEntry(entry)
    }
}