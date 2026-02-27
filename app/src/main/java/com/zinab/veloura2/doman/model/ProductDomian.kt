package com.zinab.veloura2.doman.model

import com.zinab.veloura2.data.data_source.remote.retrofit.model.ReviewX

data class ProductDomain(
    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String?,
    val imageUrl: String,
    val description: String,
    val images: List<String>,
    val colors: List<String> = listOf("Red","Blue","Green"),
    val sizes: List<String> = listOf("S","M","L"),
    val features: List<String> = listOf("Feature1","Feature2"),
    val reviews: List<Review>,
    val relatedProducts: List<ProductDomain> = listOf()
)