package com.example.testingapp.di

import com.example.testingapp.network.API
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class APIModule {
    private val baseURL = "http://api.exchangeratesapi.io/"

    @Singleton
    @Provides
    fun getRetrofitInterface(retrofit: Retrofit): API{
        return retrofit.create(API::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}