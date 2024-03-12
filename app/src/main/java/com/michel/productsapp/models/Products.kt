package com.michel.productsapp.models

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
)