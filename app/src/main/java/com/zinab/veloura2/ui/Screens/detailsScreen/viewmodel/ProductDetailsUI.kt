package com.zinab.veloura2.ui.Screens.detailsScreen.viewmodel

import com.zinab.veloura2.doman.model.Review


data class ProductDetailsUI(
    val id: Int,
    val price: Double,
    val title: String,
    val description: String,
    val images: List<String>,
    val colors: List<String> = listOf("Red", "Blue", "Green"),
    val sizes: List<String> = listOf("S", "M", "L"),
    val features: List<String> = listOf("Feature1", "Feature2"),
    val reviews: List<Review>,
    val relatedProducts: List<ProductDetailsUI> = listOf()
)