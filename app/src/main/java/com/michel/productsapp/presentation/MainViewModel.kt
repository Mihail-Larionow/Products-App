package com.michel.productsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.michel.productsapp.models.NetworkState
import com.michel.productsapp.data.repository.ProductsRepository
import com.michel.productsapp.models.Product

class MainViewModel(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    val productPagedList: LiveData<PagingData<Product>> by lazy{
        productsRepository.fetch(this.viewModelScope)
    }

    val networkState: LiveData<NetworkState> by lazy{
        productsRepository.getNetworkState()
    }

}