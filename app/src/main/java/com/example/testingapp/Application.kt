package com.example.testingapp

import android.app.Application
import android.content.Context
import com.example.testingapp.di.AppComponent
import com.example.testingapp.di.DaggerAppComponent
import com.example.testingapp.di.RoomModule

class Application: Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .roomModule(RoomModule(this))
            .build()
    }
}

val Context.appComponent: AppComponent
get() = when(this){
    is com.example.testingapp.Application -> appComponent
    else -> this.applicationContext.appComponent
}
