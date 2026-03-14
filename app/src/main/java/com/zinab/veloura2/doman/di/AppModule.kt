package com.zinab.veloura2.domain.di

import android.content.Context
import androidx.room.Room
import com.zinab.veloura2.data.data_source.remote.retrofit.api.VelouraApi
import com.zinab.veloura2.data.local.VelouraDatabase
import com.zinab.veloura2.data.local.dao.CartDao
import com.zinab.veloura2.domain.repository.CartRepository  // تصحيح الاستيراد
import com.zinab.veloura2.doman.repositry.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    // ========== Database Providers ==========
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): VelouraDatabase {
        return Room.databaseBuilder(
            context,
            VelouraDatabase::class.java,
            "veloura_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: VelouraDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepository(cartDao)  // استخدام الكلاس الموجود مباشرة
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        velouraApi: VelouraApi
    ): ProductsRepository {
        return ProductsRepository(velouraApi)
    }
}