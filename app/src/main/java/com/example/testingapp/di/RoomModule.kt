package com.example.testingapp.di

import android.app.Application
import android.content.Context
import com.example.testingapp.room.RateDao
import com.example.testingapp.room.RateDatabase
import com.example.testingapp.room.RateRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@OptIn(InternalCoroutinesApi::class)
@Module
class RoomModule(private val application: Application) {

    @Provides
    fun provideRateDao(rateDatabase: RateDatabase): RateDao{
        return rateDatabase.rateDao()
    }

    @Provides
    fun provideRateRepository(dao: RateDao): RateRepository{
        return RateRepository(dao)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): RateDatabase{
        return RateDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideContext(): Context{
        return application.applicationContext
    }
}