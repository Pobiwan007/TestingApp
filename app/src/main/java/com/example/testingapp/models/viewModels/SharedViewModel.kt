package com.example.testingapp.models.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testingapp.R
import com.example.testingapp.entities.Rate
import com.example.testingapp.entities.Rates
import com.example.testingapp.models.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.Field


sealed class State{
    object SendingState : State()
    object SuccessState : State()
    class ErrorState<T>(val message: T): State()
}

sealed class Sort{
    object SortByCharUp : Sort()
    object SortByCharDown: Sort()
    object SortByValueUp: Sort()
    object SortByValueDown: Sort()
}

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.SendingState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _currenciesList = MutableStateFlow<MutableList<Rate>>(mutableListOf())
    val currencyList: StateFlow<MutableList<Rate>> = _currenciesList.asStateFlow()
    private lateinit var list: MutableStateFlow<MutableList<Rate>>

    private val _sortListValue = MutableStateFlow<Sort>(Sort.SortByValueDown)
    val sortValue: StateFlow<Sort> = _sortListValue.asStateFlow()

    init {
        getCurrency()
    }

    private fun getCurrency(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCurrencyAsync()
            when (response.code()) {
                200 -> {
                    _state.value = State.SuccessState
                    list = MutableStateFlow(convertClassToList(response.body()?.rates!!))
                    _currenciesList.value = list.value
                }
                401 -> {
                    _state.value = State.ErrorState(message = R.string.unauthorized)
                }
                102 -> {
                    _state.value = State.ErrorState(message = R.string.inActive)
                }
                else -> {
                    _state.value = State.ErrorState(message = response.code().toString())
                }
            }
            sortList()
        }
    }

    fun searchRateByName(name: String){
        _currenciesList.value = list.value.filter { rate -> rate.nameCurrency.contains(name.uppercase()) } as MutableList<Rate>
    }

    private suspend fun sortList(){
        sortValue.collect {
            when(it){
                is Sort.SortByValueUp -> {
                    list.value.sortBy {  rate -> rate.valueCurrency.toInt()  }
                }
                is Sort.SortByCharUp -> {
                    list.value.sortBy {  rate ->  rate.nameCurrency }
                }
                is Sort.SortByValueDown -> {
                    list.value.sortByDescending {  rate -> rate.valueCurrency.toInt()  }
                }
                is Sort.SortByCharDown -> {
                    list.value.sortByDescending {  rate -> rate.nameCurrency  }
                }
            }
        }
    }

    fun setSortValue(sort: Sort){
        _sortListValue.value = sort
    }

    private fun convertClassToList(rates: Rates): MutableList<Rate>{
        val list = mutableListOf<Rate>()
        val declaredFields: Array<Field> = Rates::class.java.declaredFields
        for ((index, field) in declaredFields.withIndex()) {
            field.isAccessible = true
            list.add(Rate(
                id = index,
                nameCurrency = field.name.uppercase(),
                valueCurrency = field.get(rates)!!.toString().toDouble()
            ))
        }
        return list
    }
}
