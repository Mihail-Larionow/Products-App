package com.michel.productsapp.presentation.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.data.network.NetworkState
import com.michel.data.repository.SingleProductRepository
import com.michel.data.model.Product
import io.reactivex.disposables.CompositeDisposable

class SingleProductViewModel(private val singleProductRepository: SingleProductRepository, productId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    val singleProduct: LiveData<Product> by lazy {
        singleProductRepository.fetchProductDetails(compositeDisposable, productId)
    }

    val networkState: LiveData<NetworkState> by lazy{
        singleProductRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}