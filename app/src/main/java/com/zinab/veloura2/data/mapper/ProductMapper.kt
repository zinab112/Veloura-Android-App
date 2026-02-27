package com.zinab.veloura2.data.mapper

import com.zinab.veloura2.data.data_source.remote.retrofit.model.ProductX
import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.doman.model.Review

fun ProductX.toDomain(): ProductDomain {
    return ProductDomain(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        imageUrl = this.thumbnail ?: "",
        thumbnail = this.thumbnail,
        images = this.images ?: emptyList(),
        reviews = this.reviews?.map { reviewX ->  // تحويل ReviewX إلى Review
            Review(
                comment = reviewX.comment ?: "",
                rating = reviewX.rating ?: 0,
                reviewerName = reviewX.reviewerName ?: "",
                reviewerEmail = reviewX.reviewerEmail ?: "",  // لو حابة تحتفظي بالـ email
                date = reviewX.date ?: ""
            )
        } ?: emptyList()
    )
}