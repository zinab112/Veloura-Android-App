package com.zinab.veloura2.doman.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.zinab.veloura2.data.local.dao.CartDao
import com.zinab.veloura2.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val currentUserId: String?
        get() = auth.currentUser?.uid

    // جلب منتجات المستخدم الحالي فقط
    fun getAllCartItems(): Flow<List<CartItemEntity>> {
        val userId = currentUserId ?: return flowOf(emptyList())
        return cartDao.getAllCartItems(userId)
    }

    suspend fun addToCart(item: CartItemEntity): Result<Unit> {
        return try {
            val userId = currentUserId
                ?: return Result.failure(Exception("Please login first"))

            val itemWithUserId = item.copy(userId = userId)

            // حفظ في Room
            cartDao.insertCartItem(itemWithUserId)
            Log.d(TAG, "✅ Saved to Room for user: $userId")

            // حفظ في Firestore
            val docRef = firestore.collection("carts")
                .document(userId)
                .collection("items")
                .document(item.productId.toString())

            docRef.set(itemWithUserId).await()
            Log.d(TAG, "✅ Saved to Firestore for user: $userId")

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "❌ Failed: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateQuantity(productId: Int, newQuantity: Int) {
        val userId = currentUserId ?: return
        cartDao.updateQuantity(productId, newQuantity, userId)
        syncToFirestore(productId, newQuantity)
    }

    suspend fun removeFromCart(productId: Int) {
        val userId = currentUserId ?: return
        cartDao.deleteCartItemByProductId(productId, userId)
        Log.d(TAG, "✅ Removed from Room for user: $userId")

        try {
            firestore.collection("carts")
                .document(userId)
                .collection("items")
                .document(productId.toString())
                .delete()
                .await()
            Log.d(TAG, "✅ Removed from Firestore for user: $userId")
        } catch (e: Exception) {
            Log.e(TAG, "⚠️ Firestore delete failed: ${e.message}")
        }
    }

    suspend fun clearCart() {
        val userId = currentUserId ?: return
        cartDao.clearCart(userId)
        Log.d(TAG, "✅ Cleared Room for user: $userId")

        try {
            val items = firestore.collection("carts")
                .document(userId)
                .collection("items")
                .get()
                .await()

            val batch = firestore.batch()
            items.documents.forEach { doc ->
                batch.delete(doc.reference)
            }
            batch.commit().await()
            Log.d(TAG, "✅ Cleared Firestore for user: $userId")
        } catch (e: Exception) {
            Log.e(TAG, "⚠️ Firestore clear failed: ${e.message}")
        }
    }

    suspend fun syncFromFirestore() {
        val userId = currentUserId ?: return

        try {
            val snapshot = firestore.collection("carts")
                .document(userId)
                .collection("items")
                .get()
                .await()

            val items = snapshot.documents.mapNotNull { doc ->
                doc.toObject(CartItemEntity::class.java)
            }

            if (items.isNotEmpty()) {
                cartDao.clearCart(userId)
                items.forEach { item ->
                    cartDao.insertCartItem(item)
                }
                Log.d(TAG, "✅ Synced ${items.size} items for user: $userId")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ Firestore sync failed: ${e.message}")
        }
    }

    private suspend fun syncToFirestore(productId: Int, newQuantity: Int) {
        val userId = currentUserId ?: return

        try {
            firestore.collection("carts")
                .document(userId)
                .collection("items")
                .document(productId.toString())
                .update("quantity", newQuantity)
                .await()
            Log.d(TAG, "✅ Synced quantity to Firestore for user: $userId")
        } catch (e: Exception) {
            Log.e(TAG, "⚠️ Sync failed: ${e.message}")
        }
    }
}