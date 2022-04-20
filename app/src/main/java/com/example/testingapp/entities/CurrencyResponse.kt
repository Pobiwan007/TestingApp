package com.example.testingapp.entities

import com.google.gson.annotations.SerializedName

data class CurrencyResponse (
    @SerializedName("success")
    val success: String ?= null,

    @SerializedName("rates")
    val rates: Rates ?= null
)