package com.example.pizzeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.model.Repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(val productId: String) : ViewModel() {
    private val productRepository = ProductRepository()

    private val _product = MutableStateFlow<ProductData?>(null)
    val product: StateFlow<ProductData?> = _product

    init {
        fetchProductDetail()
    }

    private fun fetchProductDetail() {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }
}
