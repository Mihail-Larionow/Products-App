package com.michel.productsapp.presentation.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michel.data.repository.ProductDetailsRepository

class SingleProductViewModelFactory(
    private val productDetailsRepository: ProductDetailsRepository,
    private val productId: Int
): ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleProductViewModel(productDetailsRepository, productId = productId) as T
    }
}