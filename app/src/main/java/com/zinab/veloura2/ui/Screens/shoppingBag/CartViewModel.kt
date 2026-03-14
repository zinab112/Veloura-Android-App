package com.zinab.veloura2.ui.Screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.zinab.veloura2.data.local.entity.CartItemEntity
import com.zinab.veloura2.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(

    private val cartRepository: CartRepository
) : ViewModel() {


    val cartItems: StateFlow<List<CartItemEntity>> = cartRepository.getAllCartItems()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalPrice: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val itemCount: StateFlow<Int> = cartItems.map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun increaseQuantity(productId: Int, currentQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(productId, currentQuantity + 1)
        }
    }

    fun decreaseQuantity(productId: Int, currentQuantity: Int) {
        if (currentQuantity > 1) {
            viewModelScope.launch {
                cartRepository.updateQuantity(productId, currentQuantity - 1)
            }
        }
    }

    fun removeItem(productId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}