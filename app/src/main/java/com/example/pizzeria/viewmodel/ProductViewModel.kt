package com.example.pizzeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.model.Repository.CategoryRepository
import com.example.pizzeria.model.Repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel:ViewModel() {
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
    fun getProductByName(productName: String?): StateFlow<ProductData?> {
        val productFlow = MutableStateFlow<ProductData?>(null)
        viewModelScope.launch {
            repository.getProductByName(productName).collect { product ->
                productFlow.value = product
            }
        }
        return productFlow
    }
}