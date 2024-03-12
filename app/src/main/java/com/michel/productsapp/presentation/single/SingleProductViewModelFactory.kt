package com.michel.productsapp.presentation.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michel.productsapp.data.repository.SingleProductRepository

@Suppress("UNCHECKED_CAST")
class SingleProductViewModelFactory(
    private val singleProductRepository: SingleProductRepository,
    private val productId: Int
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleProductViewModel(
            singleProductRepository = singleProductRepository,
            productId = productId
        ) as T
    }

}