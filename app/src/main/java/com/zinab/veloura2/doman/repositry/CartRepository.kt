package com.zinab.veloura2.domain.repository

import com.zinab.veloura2.data.local.dao.CartDao
import com.zinab.veloura2.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    fun getAllCartItems(): Flow<List<CartItemEntity>> = cartDao.getAllCartItems()

    suspend fun addToCart(cartItem: CartItemEntity) {
        cartDao.insertCartItem(cartItem)
    }

    suspend fun updateQuantity(productId: Int, quantity: Int) {
        cartDao.updateQuantity(productId, quantity)
    }

    suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItemByProductId(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}