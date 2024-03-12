package com.michel.data.model

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
)