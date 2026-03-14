package com.zinab.veloura2.ui.Screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinab.veloura2.data.local.entity.CartItemEntity
import com.zinab.veloura2.doman.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val cartItems: StateFlow<List<CartItemEntity>> = cartRepository.getAllCartItems()
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

    // ✅ دالة المزامنة من السحاب
    fun syncCart() {
        viewModelScope.launch {
            _isLoading.value = true
            cartRepository.syncFromFirestore()
            _isLoading.value = false
        }
    }

    // ✅ دالة لإضافة منتج للسحاب والمحلي
    fun addToFirestore(item: CartItemEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = cartRepository.addToCart(item)  // ✅ صح
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to add"
            }
            _isLoading.value = false
        }
    }
    // أضيفي هذه الدالة في CartViewModel.kt (بعد addToFirestore)

    fun addToCart(item: CartItemEntity) {

        viewModelScope.launch {
            _isLoading.value = true
            val result = cartRepository.addToCart(item)
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to add to cart"
            }
            _isLoading.value = false
        }
    }

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

    fun clearError() {
        _errorMessage.value = null
    }
}