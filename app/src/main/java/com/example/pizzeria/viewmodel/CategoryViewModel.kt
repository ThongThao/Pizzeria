package com.example.pizzeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.Repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    private val categoryRepository = CategoryRepository()

    private val _categories = MutableStateFlow<List<CategoryData>>(emptyList())
    val categories: StateFlow<List<CategoryData>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = categoryRepository.getCategories()
        }
    }


}