package com.example.testingapp.di

import com.example.testingapp.network.API
import com.example.testingapp.models.repository.CurrencyRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(api: API): CurrencyRepository{
        return CurrencyRepository(api)
    }
}