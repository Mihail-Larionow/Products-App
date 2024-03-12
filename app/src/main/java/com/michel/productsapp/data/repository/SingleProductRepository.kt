package com.michel.productsapp.data.repository

import androidx.lifecycle.LiveData
import com.michel.productsapp.data.api.ProductAPI
import com.michel.productsapp.models.NetworkState
import com.michel.productsapp.data.datasource.SingleProductDataSource
import com.michel.productsapp.models.Product
import io.reactivex.disposables.CompositeDisposable

class SingleProductRepository(private val productAPI: ProductAPI) {

    private lateinit var singleProductDataSource: SingleProductDataSource

    fun fetch(compositeDisposable: CompositeDisposable, productId: Int): LiveData<Product> {

        singleProductDataSource = SingleProductDataSource(
            productAPI = productAPI,
            compositeDisposable = compositeDisposable
        )

        singleProductDataSource.fetch(productId = productId)

        return singleProductDataSource.singleProduct
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return singleProductDataSource.networkState
    }
}