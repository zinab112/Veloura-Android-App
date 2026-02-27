package com.zinab.veloura2.data.data_source.remote.retrofit.model

data class ProductsResponse(
    val limit: Int,
    val products: List<ProductX>,
    val skip: Int,
    val total: Int
)