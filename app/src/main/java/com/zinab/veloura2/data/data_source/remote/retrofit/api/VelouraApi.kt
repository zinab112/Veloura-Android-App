package com.zinab.veloura2.data.data_source.remote.retrofit.api

import com.zinab.veloura2.data.data_source.remote.retrofit.model.ProductX
import com.zinab.veloura2.data.data_source.remote.retrofit.model.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface VelouraApi {

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): ProductsResponse

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
        const val IMAGE_BASE_URL = "https://cdn.dummyjson.com/"
    }
    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductX
}
