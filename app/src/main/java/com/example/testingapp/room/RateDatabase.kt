package com.example.testingapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testingapp.entities.Rate
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Rate::class], version = 1, exportSchema = false)
abstract class RateDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao

    companion object{
        @Volatile
        private var INSTANCE: RateDatabase?= null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): RateDatabase {
            val instance = INSTANCE
            if(instance != null){
                return instance
            }
            synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RateDatabase::class.java,
                    "rate").build()
                return INSTANCE as RateDatabase
            }
        }
    }
}