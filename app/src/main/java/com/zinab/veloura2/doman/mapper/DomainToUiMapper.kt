package com.zinab.veloura2.domain.mapper

import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.ui.Screens.homeScreen.viewmodel.ProductUiModel

fun ProductDomain.toUiModel(): ProductUiModel {
    return ProductUiModel(
        id = this.id,
        title = this.title,
        price = this.price,
        imageUrl = this.imageUrl

    )
}