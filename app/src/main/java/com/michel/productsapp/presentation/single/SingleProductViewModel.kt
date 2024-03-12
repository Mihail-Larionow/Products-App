package com.michel.productsapp.presentation.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.productsapp.models.NetworkState
import com.michel.productsapp.data.repository.SingleProductRepository
import com.michel.productsapp.models.Product
import io.reactivex.disposables.CompositeDisposable

class SingleProductViewModel(
    singleProductRepository: SingleProductRepository,
    productId: Int
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val singleProduct: LiveData<Product> by lazy {
        singleProductRepository.fetch(
            compositeDisposable = compositeDisposable,
            productId = productId
        )
    }

    val networkState: LiveData<NetworkState> by lazy{
        singleProductRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}