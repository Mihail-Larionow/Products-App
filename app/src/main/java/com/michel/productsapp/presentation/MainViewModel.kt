package com.michel.productsapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.domain.usecase.GetDataUseCase
import com.michel.productsapp.model.ProductsModel

class MainViewModel(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {

    init{
        Log.v("APP", "ViewModel starting");
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getData(): ProductsModel{
        val data = getDataUseCase.execute()
        val products = ProductsModel(data.something)
        return products
    }

}