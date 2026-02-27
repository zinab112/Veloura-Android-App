package com.zinab.veloura2.ui.Screens.detailsScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.doman.repositry.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    var product: MutableState<ProductDomain?> = mutableStateOf(null)
        private set

    var selectedColor: MutableState<String?> = mutableStateOf(null)
    fun selectColor(color: String) { selectedColor.value = color }

    var selectedSize: MutableState<String?> = mutableStateOf(null)
    fun selectSize(size: String) { selectedSize.value = size }

    var quantity: MutableState<Int> = mutableStateOf(1)
    fun increaseQuantity() { quantity.value += 1 }
    fun decreaseQuantity() { if (quantity.value > 1) quantity.value -= 1 }

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
}