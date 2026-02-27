package com.zinab.veloura2.doman.model
//
//// Domain model for User
data class Review(
    val comment: String,
    val rating: Int,
    val date: String,
    val reviewerEmail: String,
    val reviewerName: String
)