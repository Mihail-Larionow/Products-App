package com.michel.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.michel.data.api.DEFAULT_LIMIT
import com.michel.data.api.ProductAPI
import com.michel.data.datasource.ProductsDataSourceFactory
import com.michel.data.network.NetworkState
import com.michel.data.model.Product

class ProductsRepository(private val productAPI: ProductAPI) {

    private lateinit var productList: LiveData<PagingData<Product>>
    private lateinit var productsDataSourceFactory: ProductsDataSourceFactory

    fun fetch(): LiveData<PagingData<Product>>{
        productsDataSourceFactory = ProductsDataSourceFactory(productAPI = productAPI)

        val config = PagingConfig(
            pageSize = DEFAULT_LIMIT,
            enablePlaceholders = false
        )

        val pager = Pager(
            config = config,
            initialKey = null,
            pagingSourceFactory = productsDataSourceFactory
        )

        productList = pager.liveData

        return productList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return productsDataSourceFactory.productsDataSource.switchMap{
            return@switchMap it.networkState
        }

    }

}