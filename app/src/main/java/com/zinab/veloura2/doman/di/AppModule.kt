package com.zinab.veloura2.doman.di
 // ✅ غيري من doman إلى domain

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.zinab.veloura2.data.local.VelouraDatabase
import com.zinab.veloura2.data.local.dao.CartDao
import com.zinab.veloura2.doman.repository.CartRepository
import com.zinab.veloura2.data.data_source.remote.retrofit.api.VelouraApi
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
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()  // ✅ جديد

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
        return VelouraDatabase.getInstance(context)  // ✅ استخدمي الـ singleton
    }

    @Provides
    @Singleton
    fun provideCartDao(database: VelouraDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao,
        firestore: FirebaseFirestore,  // ✅ أضيفي
        auth: FirebaseAuth              // ✅ أضيفي
    ): CartRepository {
        return CartRepository(cartDao, firestore, auth)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        velouraApi: VelouraApi
    ): ProductsRepository {
        return ProductsRepository(velouraApi)
    }
}