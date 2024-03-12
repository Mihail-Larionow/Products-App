package com.michel.productsapp.data.api

import com.michel.productsapp.models.Product
import com.michel.productsapp.models.Products
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductAPI {

    @GET("products/{product_id}")
    fun getProductDetails(@Path("product_id") id: Int): Single<Product>

    @GET("products")
    suspend fun getProducts(@Query("skip") skip: Int, @Query("limit") limit: Int): Response<Products>

}