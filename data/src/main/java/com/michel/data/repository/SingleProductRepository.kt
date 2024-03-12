package com.michel.data.repository

import androidx.lifecycle.LiveData
import com.michel.data.api.ProductAPI
import com.michel.data.network.NetworkState
import com.michel.data.datasource.SingleProductDataSource
import com.michel.data.model.Product
import io.reactivex.disposables.CompositeDisposable

class SingleProductRepository(private val productAPI: ProductAPI) {

    private lateinit var singleProductDataSource: SingleProductDataSource

    fun fetchProductDetails(compositeDisposable: CompositeDisposable, productId: Int): LiveData<Product> {

        singleProductDataSource = SingleProductDataSource(productAPI, compositeDisposable)
        singleProductDataSource.fetch(productId)

        return singleProductDataSource.singleProduct
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return singleProductDataSource.networkState
    }
}