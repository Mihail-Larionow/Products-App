package com.michel.data.api

import com.michel.data.value.Product
import com.michel.data.value.Products
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IProductDB {

    @GET("{product_id}")
    fun getProductDetails(@Path("product_id") id: Int): Single<Product>

}