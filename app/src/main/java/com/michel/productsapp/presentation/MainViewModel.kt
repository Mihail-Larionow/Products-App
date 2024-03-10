package com.michel.productsapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michel.domain.usecase.LoadDataUseCase
import com.michel.productsapp.model.LoadEvent
import com.michel.productsapp.model.MainEvent
import com.michel.productsapp.model.MainState

class MainViewModel(
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    private val stateMutable = MutableLiveData<MainState>()
    val state: LiveData<MainState> = stateMutable

    init{
        Log.v("APP", "ViewModel starting")
        stateMutable.value =  MainState(something = "nothing")
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun send(event: MainEvent){
        when(event){
            is LoadEvent -> loadData()
        }
    }

    private fun loadData() {

        val data = loadDataUseCase.execute()
        stateMutable.value = MainState(
            something = data.something
        )

    }

}