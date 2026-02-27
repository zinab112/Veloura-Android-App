package com.zinab.veloura2.doman.mapper

import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.ui.Screens.detailsScreen.viewmodel.ProductDetailsUI

fun ProductDomain.toDetailsUiModel(): ProductDetailsUI {
    return ProductDetailsUI(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        images = this.images,
        reviews = this.reviews,  // ✅ الآن هي List<Review> مباشرة
        colors = this.colors,
        sizes = this.sizes,
        features = this.features
    )
}