package com.example.pizzeria.model.Repository

import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.ProductData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance().collection("Product")

    suspend fun getProducts(): List<ProductData> {
        return try {
            val snapshot = db.get().await()
            snapshot.documents.mapNotNull { it.toObject(ProductData::class.java)?.apply { productID = it.id } }
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getProductById(productId: String): ProductData? {
        return try {
            val document = db.document(productId).get().await()
            document.toObject(ProductData::class.java)?.apply { productID = document.id }
        } catch (e: Exception) {
            null
        }
    }


}