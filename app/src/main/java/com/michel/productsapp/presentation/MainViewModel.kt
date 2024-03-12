package com.michel.productsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.michel.data.network.NetworkState
import com.michel.data.repository.ProductsRepository
import com.michel.data.model.Product
import com.michel.productsapp.model.LoadEvent
import com.michel.productsapp.model.MainEvent

class MainViewModel(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    val productPagedList: LiveData<PagingData<Product>> by lazy{
        productsRepository.fetch()
    }

    val networkState: LiveData<NetworkState> by lazy{
        productsRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean{
        return productPagedList.value == null
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

    }

}