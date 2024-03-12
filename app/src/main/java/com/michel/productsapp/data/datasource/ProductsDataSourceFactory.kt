package com.michel.productsapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import com.michel.productsapp.data.api.ProductAPI
import com.michel.productsapp.models.Product

class ProductsDataSourceFactory(private val productAPI: ProductAPI): PagingSourceFactory<Int, Product> {

    val productsDataSource = MutableLiveData<ProductsDataSource>()

    override fun invoke(): PagingSource<Int, Product> {
        val dataSource = ProductsDataSource(productAPI = productAPI)
        productsDataSource.postValue(dataSource)
        return dataSource
    }
}