package com.example.testingapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate")
data class Rate (
    @PrimaryKey
    val id: Int,
    val nameCurrency: String,
    val valueCurrency: Double
)