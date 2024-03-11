package com.michel.data.repository

import androidx.lifecycle.LiveData
import com.michel.data.api.IProductDB
import com.michel.data.network.NetworkState
import com.michel.data.network.SingleProductDataSource
import com.michel.data.value.Product
import io.reactivex.disposables.CompositeDisposable

class ProductDetailsRepository(private val iProductDB: IProductDB) {

    lateinit var productDataSource: SingleProductDataSource

    fun fetchProductDetails(compositeDisposable: CompositeDisposable, productId: Int): LiveData<Product> {

        productDataSource = SingleProductDataSource(iProductDB, compositeDisposable)
        productDataSource.fetchProductDetails(productId)

        return productDataSource.productDetailsResponse
    }

    fun getProductDetailsNetworkState(): LiveData<NetworkState>{
        return productDataSource.networkState
    }
}