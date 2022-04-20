package com.example.testingapp.di

import com.example.testingapp.fragments.CurrencyFragment
import com.example.testingapp.room.RateRoomViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class, RepositoryModule::class, ViewModelFactoryModule::class, RoomModule::class])
interface AppComponent {
    fun inject(currencyFragment: CurrencyFragment)
    fun inject(rateRoomViewModel: RateRoomViewModel)
}