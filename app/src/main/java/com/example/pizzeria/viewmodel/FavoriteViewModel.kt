package com.example.pizzeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.model.favorite
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FavoriteViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val favoritesCollection = db.collection("favorites")
    private val productsCollection = db.collection("Product")

    private val _favorite = MutableStateFlow<List<ProductData>>(emptyList())
    val favoriteProducts: StateFlow<List<ProductData>> get() = _favorite
    fun fetchFavoriteProducts(userId: String) {
        viewModelScope.launch {
            val favoriteDoc = favoritesCollection.document(userId).get().await()
            if (favoriteDoc.exists()) {
                val favorites = favoriteDoc.toObject(favorite::class.java)
                val productIds = favorites?.items.orEmpty()
                val productDetails = productIds.mapNotNull { id ->
                    productsCollection.document(id).get().await().toObject(ProductData::class.java)
                }
                _favorite.value = productDetails
            }
        }
    }
    suspend fun addFavorite(userId: String, productId: String) {
        val favoriteDoc = favoritesCollection.document(userId).get().await()
        if (favoriteDoc.exists()) {
            val favorite = favoriteDoc.toObject(favorite::class.java)
            favorite?.items = favorite?.items.orEmpty().toMutableList().apply {
                if (!contains(productId)) add(productId)
            }
            if (favorite != null) {
                favoritesCollection.document(userId).set(favorite).await()
            }
        } else {
            val newFavorite = favorite(
                favoriteId = UUID.randomUUID().toString(),
                customerId = userId,
                items = listOf(productId)
            )
            favoritesCollection.document(userId).set(newFavorite).await()
        }
    }

    suspend fun removeFavorite(userId: String, productId: String) {
        val favoriteDoc = favoritesCollection.document(userId).get().await()
        if (favoriteDoc.exists()) {
            val favorite = favoriteDoc.toObject(favorite::class.java)
            favorite?.items = favorite?.items.orEmpty().toMutableList().apply {
                remove(productId)
            }
            if (favorite != null) {
                favoritesCollection.document(userId).set(favorite).await()
            }
        }
    }

    suspend fun isFavorite(userId: String, productId: String): Boolean {
        val favoriteDoc = favoritesCollection.document(userId).get().await()
        if (favoriteDoc.exists()) {
            val favorite = favoriteDoc.toObject(favorite::class.java)
            return favorite?.items.orEmpty().contains(productId)
        }
        return false
    }
}
