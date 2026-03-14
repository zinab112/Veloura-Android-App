package com.zinab.veloura2.ui.Screens.detailsScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinab.veloura2.data.local.entity.CartItemEntity  // أضف هذا الاستيراد
import com.zinab.veloura2.domain.repository.CartRepository
import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.doman.repositry.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    var product: MutableState<ProductDomain?> = mutableStateOf(null)
        private set

    var selectedColor: MutableState<String?> = mutableStateOf(null)
        private set

    var selectedSize: MutableState<String?> = mutableStateOf(null)
        private set

    var quantity: MutableState<Int> = mutableStateOf(1)
        private set

    fun selectColor(color: String) {
        selectedColor.value = color
    }

    fun selectSize(size: String) {
        selectedSize.value = size
    }

    fun increaseQuantity() {
        quantity.value += 1
    }

    fun decreaseQuantity() {
        if (quantity.value > 1) quantity.value -= 1
    }

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            try {
                val p: ProductDomain = repository.getProductById(productId)
                product.value = p
                // defaults مؤقتة
                selectedColor.value = p.colors.firstOrNull() ?: "Red"
                selectedSize.value = p.sizes.firstOrNull() ?: "M"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToCart() {
        val currentProduct = product.value ?: return
        val currentSize = selectedSize.value ?: return
        val currentColor = selectedColor.value ?: return
        val currentQuantity = quantity.value

        viewModelScope.launch {
            try {
                // تحويل ProductDomain إلى CartItemEntity
                val cartItem = CartItemEntity(
                    productId = currentProduct.id,
                    title = currentProduct.title,
                    price = currentProduct.price,
                    imageUrl = currentProduct.images.firstOrNull() ?: "",
                    color = currentColor,
                    size = currentSize,
                    quantity = currentQuantity
                )

                // إضافة المنتج إلى السلة
                cartRepository.addToCart(cartItem)

                println("Product ${currentProduct.title} added to cart successfully!")

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error adding product to cart: ${e.message}")
            }
        }
    }
}