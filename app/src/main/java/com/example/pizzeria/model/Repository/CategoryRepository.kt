package com.example.pizzeria.model.Repository

import com.example.pizzeria.model.CategoryData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepository {
    private val db = FirebaseFirestore.getInstance().collection("Category")

    suspend fun getCategories(): List<CategoryData> {
        return try {
            val snapshot = db.get().await()
            snapshot.documents.mapNotNull { it.toObject(CategoryData::class.java)?.apply { categoryID = it.id } }
        } catch (e: Exception) {
            emptyList()
        }
    }
}