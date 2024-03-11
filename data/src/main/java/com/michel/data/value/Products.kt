package com.michel.data.value

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)