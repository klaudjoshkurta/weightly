package com.shkurta.weighttracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shkurta.weighttracker.data.local.dao.WeightDao
import com.shkurta.weighttracker.data.local.entity.WeightEntry

@Database(entities = [WeightEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao
}