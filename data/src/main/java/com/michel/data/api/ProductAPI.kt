package com.michel.data.api

import com.michel.data.model.Product
import com.michel.data.model.Products
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