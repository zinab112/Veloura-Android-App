package com.zinab.veloura2.data.local.dao

import androidx.room.*
import com.zinab.veloura2.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // ✅ جلب منتجات مستخدم معين
    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getAllCartItems(userId: String): Flow<List<CartItemEntity>>

    // ✅ جلب منتج معين لمستخدم معين
    @Query("SELECT * FROM cart_items WHERE productId = :productId AND userId = :userId")
    suspend fun getCartItemByProductId(productId: Int, userId: String): CartItemEntity?

    // ✅ إضافة منتج لمستخدم معين
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    // ✅ تحديث كمية منتج لمستخدم معين
    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId AND userId = :userId")
    suspend fun updateQuantity(productId: Int, quantity: Int, userId: String)

    // ✅ حذف منتج لمستخدم معين
    @Query("DELETE FROM cart_items WHERE productId = :productId AND userId = :userId")
    suspend fun deleteCartItemByProductId(productId: Int, userId: String)

    // ✅ مسح كل منتجات مستخدم معين
    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: String)

    // ✅ جلب كل المنتجات (للمقارنة فقط - مش للاستخدام العام)
    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItemsList(): List<CartItemEntity>

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    suspend fun getAllCartItemsListForUser(userId: String): List<CartItemEntity>
}