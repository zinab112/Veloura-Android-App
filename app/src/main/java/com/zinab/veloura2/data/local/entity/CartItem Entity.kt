package com.zinab.veloura2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @PropertyName("product_id")
    val productId: Int = 0,  // ✅ أضيفي القيم الافتراضية

    @PropertyName("title")
    val title: String = "",   // ✅ أضيفي القيم الافتراضية

    @PropertyName("price")
    val price: Double = 0.0,  // ✅ أضيفي القيم الافتراضية

    @PropertyName("old_price")
    val oldPrice: Double? = null,

    @PropertyName("image_url")
    val imageUrl: String = "",  // ✅ أضيفي القيم الافتراضية

    @PropertyName("color")
    val color: String = "",  // ✅ أضيفي القيم الافتراضية

    @PropertyName("size")
    val size: String = "",  // ✅ أضيفي القيم الافتراضية

    @PropertyName("quantity")
    var quantity: Int = 1,  // ✅ أضيفي القيم الافتراضية

    @PropertyName("user_id")
    val userId: String = ""  // ✅ أضيفي القيم الافتراضية
)