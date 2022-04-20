package com.example.testingapp.models.repository


import com.example.testingapp.network.API
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val common: API)  {
    suspend fun getCurrencyAsync() = common.getCurrency()
}