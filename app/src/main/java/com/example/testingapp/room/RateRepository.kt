package com.example.testingapp.room

import com.example.testingapp.entities.Rate

class RateRepository (private val dao: RateDao){
    val currencies = dao.getAll()

    suspend fun add(rate: Rate){
        dao.add(rate)
    }

    suspend fun delete(rate: Rate){
        dao.delete(rate)
    }
}