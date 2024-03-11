package com.michel.productsapp.presentation.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.data.network.NetworkState
import com.michel.data.repository.ProductDetailsRepository
import com.michel.data.value.Product
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingleProductViewModel(private val productDetailsRepository: ProductDetailsRepository, productId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    val productDetails: LiveData<Product> by lazy {
        productDetailsRepository.fetchProductDetails(compositeDisposable, productId)
    }

    val networkState: LiveData<NetworkState> by lazy{
        productDetailsRepository.getProductDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}