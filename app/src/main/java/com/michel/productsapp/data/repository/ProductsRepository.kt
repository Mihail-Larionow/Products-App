package com.michel.productsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.michel.productsapp.data.api.DEFAULT_LIMIT
import com.michel.productsapp.data.api.ProductAPI
import com.michel.productsapp.data.datasource.ProductsDataSourceFactory
import com.michel.productsapp.models.NetworkState
import com.michel.productsapp.models.Product
import kotlinx.coroutines.CoroutineScope

class ProductsRepository(private val productAPI: ProductAPI) {

    private lateinit var productsDataSourceFactory: ProductsDataSourceFactory

    fun fetch(scope: CoroutineScope): LiveData<PagingData<Product>>{
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

        return pager.liveData.cachedIn(scope)
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return productsDataSourceFactory.productsDataSource.switchMap{
            return@switchMap it.networkState
        }

    }

}