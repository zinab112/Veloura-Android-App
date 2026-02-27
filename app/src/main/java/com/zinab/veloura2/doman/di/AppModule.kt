package com.zinab.veloura2.domain.di

import com.zinab.veloura2.data.data_source.remote.retrofit.api.VelouraApi
import com.zinab.veloura2.doman.repositry.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(VelouraApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVelouraApi(retrofit: Retrofit): VelouraApi {
        return retrofit.create(VelouraApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        velouraApi: VelouraApi
    ): ProductsRepository {
        return ProductsRepository(velouraApi)
    }
}