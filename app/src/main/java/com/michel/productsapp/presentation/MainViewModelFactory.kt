package com.michel.productsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michel.data.repository.ProductsRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val productsRepository: ProductsRepository
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(productsRepository) as T
    }

}