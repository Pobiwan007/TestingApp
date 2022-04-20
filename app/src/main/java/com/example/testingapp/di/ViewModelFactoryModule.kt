package com.example.testingapp.di

import com.example.testingapp.models.repository.CurrencyRepository
import com.example.testingapp.models.viewModels.SharedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {
    @Provides
    fun provideSharedViewModelFactory(currencyRepository: CurrencyRepository): SharedViewModelFactory{
        return SharedViewModelFactory(repository = currencyRepository)
    }
}