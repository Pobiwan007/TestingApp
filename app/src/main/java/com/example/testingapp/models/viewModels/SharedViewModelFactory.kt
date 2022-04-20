package com.example.testingapp.models.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testingapp.models.repository.CurrencyRepository

class SharedViewModelFactory(private val repository: CurrencyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            CurrencyViewModel(this.repository) as T
        } else
            throw IllegalArgumentException("ViewModel Not Found")
    }
}