package com.michel.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import com.michel.data.api.ProductAPI
import com.michel.data.model.Product

class ProductsDataSourceFactory(private val productAPI: ProductAPI): PagingSourceFactory<Int, Product> {

    val productsDataSource = MutableLiveData<ProductsDataSource>()

    override fun invoke(): PagingSource<Int, Product> {
        val dataSource = ProductsDataSource(productAPI)

        productsDataSource.postValue(dataSource)

        return dataSource
    }
}