package com.zinab.veloura2.ui.Screens.homeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.doman.repositry.ProductsRepository
import com.zinab.veloura2.ui.Screens.homeScreen.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    // كل المنتجات (للـ All Item)
    private val _allProducts = MutableStateFlow<List<ProductDomain>>(emptyList())
    val allProducts: StateFlow<List<ProductDomain>> = _allProducts

    // المنتجات الحالية المعروضة (حسب الكاتيجوري المختارة)
    private val _currentProducts = MutableStateFlow<List<ProductDomain>>(emptyList())
    val currentProducts: StateFlow<List<ProductDomain>> = _currentProducts

    // الكاتيجوري المختارة
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    // Promos
    private val _promosForYou = MutableStateFlow<List<ProductDomain>>(emptyList())
    val promosForYou: StateFlow<List<ProductDomain>> = _promosForYou

    init {
        // نجيب كل المنتجات أول ما الشاشة تفتح
        loadAllProducts()
        loadPromos()
    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category

        // لو الكاتيجوري هي All Item (id = 0)
        if (category.id == 0) {
            _currentProducts.value = _allProducts.value
        } else {
            // لو كاتيجوري تانية، نجيبها من API
            loadProductsForCategory(category)
        }
    }

    private fun loadAllProducts() {
        viewModelScope.launch {
            try {
                val products = repository.getNewArrivals()
                _allProducts.value = products
                _currentProducts.value = products // في الأول نعرض كل المنتجات
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun loadProductsForCategory(category: Category) {
        viewModelScope.launch {
            try {
                val products = repository.getProductsByCategory(category.apiCategory)
                _currentProducts.value = products
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun loadPromos() {
        viewModelScope.launch {
            val products = repository.getNewArrivals()
            _promosForYou.value = products.shuffled().take(10)
        }
    }
}