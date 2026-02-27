package com.zinab.veloura2.doman.repositry

import android.util.Log
import com.zinab.veloura2.data.data_source.remote.retrofit.api.VelouraApi
import com.zinab.veloura2.data.mapper.toDomain
import com.zinab.veloura2.doman.model.ProductDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val velouraApi: VelouraApi,
) {

    // ✅ دالة تجيب المنتجات حسب الكاتيجوري
    suspend fun getProductsByCategory(categoryApiName: String?): List<ProductDomain> {
        // لو الكاتيجوري null أو "All Item"، نجيب كل حاجة
        if (categoryApiName == null) {
            return getAllProducts()
        }

        // لو فيه كاتيجوري محددة، نجيب منها بس
        return try {
            Log.d("API_TEST", "Fetching category: $categoryApiName")
            val response = velouraApi.getProductsByCategory(categoryApiName)
            response.products.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("API_TEST", "Error fetching category $categoryApiName", e)
            emptyList()
        }
    }

    // ✅ دالة مساعدة تجيب كل المنتجات
    private suspend fun getAllProducts(): List<ProductDomain> {
        Log.d("API_TEST", "Fetching All Products...")
        val categories = listOf(
            "womens-dresses",
            "womens-bags",
            "womens-shoes",
            "womens-jewellery",
            "womens-watches"
        )

        val products = categories.flatMap { category ->
            Log.d("API_TEST", "Fetching category: $category")
            val result = velouraApi.getProductsByCategory(category).products
            Log.d("API_TEST", "Category $category returned ${result.size} items")
            result
        }

        Log.d("API_TEST", "Total Products: ${products.size}")
        return products.map { it.toDomain() }
    }

    // ✅ الـ function القديمة (للتوافق)
    suspend fun getNewArrivals(): List<ProductDomain> {
        return getAllProducts()
    }

    suspend fun getProductById(id: Int): ProductDomain {
        try {
            val productX = velouraApi.getProductById(id)
            return productX.toDomain()
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching product $id", e)
            throw e
        }
    }
}