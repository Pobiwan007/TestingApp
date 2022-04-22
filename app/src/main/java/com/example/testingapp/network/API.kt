package com.example.testingapp.network

import com.example.testingapp.entities.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface API {
    @GET("/v1/latest?access_key=8976ef4e944136340f929e32c5c82e6e")
    suspend fun getCurrency(): Response<CurrencyResponse>

    @GET("/v1/latest?access_key=8976ef4e944136340f929e32c5c82e6e")
    suspend fun getBasedCurrency(@Query("base") base: String): Response<CurrencyResponse>
}