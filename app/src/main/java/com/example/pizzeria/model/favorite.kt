package com.example.pizzeria.model

data class favorite(
    var favoriteId: String = "",
    var customerId: String = "",
    var items: List<String>? = null,
)
