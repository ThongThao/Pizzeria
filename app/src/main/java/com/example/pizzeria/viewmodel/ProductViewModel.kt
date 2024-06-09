package com.example.pizzeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.Order
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.model.Repository.CategoryRepository
import com.example.pizzeria.model.Repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel():ViewModel() {
    private val productRepository = ProductRepository()
    private val repository: ProductRepository = ProductRepository()
    private val _products = MutableStateFlow<List<ProductData>>(emptyList())
    val products: MutableStateFlow<List<ProductData>> = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _products.value = productRepository.getProducts()
        }
    }
    private val _product1 = MutableStateFlow<List<ProductData>>(emptyList())
    val product1: StateFlow<List<ProductData>> = _product1
    private val firestore = FirebaseFirestore.getInstance()
    private val db = firestore.collection("Product")
    fun fetchProductByCategory(categoryName: String) {
        db.whereEqualTo("productType", categoryName)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let { querySnapshot ->
                    val productList = mutableListOf<ProductData>()
                    for (document in querySnapshot.documents) {
                        val order = document.toObject(ProductData::class.java)
                        order?.let { productList.add(it) }
                    }
                    _product1.value = productList
                }
            }
    }


}