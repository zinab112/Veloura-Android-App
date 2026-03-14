
package com.zinab.veloura2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val oldPrice: Double? = null,
    val imageUrl: String,
    val color: String,
    val size: String,
    val quantity: Int
)