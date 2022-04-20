package com.example.testingapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testingapp.entities.Rate

@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(rate: Rate)

    @Query("SELECT * FROM rate")
    fun getAll(): LiveData<List<Rate>>

    @Delete
    suspend fun delete(rate: Rate)
}