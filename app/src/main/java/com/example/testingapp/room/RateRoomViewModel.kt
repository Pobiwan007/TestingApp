package com.example.testingapp.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testingapp.appComponent
import com.example.testingapp.entities.Rate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RateRoomViewModel(app: Application): AndroidViewModel(app) {
    val allRates: LiveData<List<Rate>>
    @Inject
    lateinit var repository: RateRepository
    private val _isEmptyList: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isEmptyList: StateFlow<Boolean> = _isEmptyList.asStateFlow()

    init {
        app.appComponent.inject(this)
        allRates = repository.currencies
        updateEmptyList()
    }

    fun updateEmptyList(){
        _isEmptyList.value = allRates.value?.isNotEmpty() != true
    }

    fun add(rate: Rate){
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(rate)
        }
    }

    fun delete(rate: Rate){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(rate)
        }
    }
}