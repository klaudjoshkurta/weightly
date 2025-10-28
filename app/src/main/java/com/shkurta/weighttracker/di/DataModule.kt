package com.shkurta.weighttracker.di

import android.content.Context
import androidx.room.Room
import com.shkurta.weighttracker.data.local.dao.WeightDao
import com.shkurta.weighttracker.data.local.db.AppDatabase
import com.shkurta.weighttracker.data.repository.WeightRepository
import com.shkurta.weighttracker.data.repository.WeightRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "weight_db").build()

    @Provides
    fun provideWeightDao(db: AppDatabase): WeightDao = db.weightDao()

    @Provides
    @Singleton
    fun provideRepository(dao: WeightDao): WeightRepository = WeightRepositoryImpl(dao)
}