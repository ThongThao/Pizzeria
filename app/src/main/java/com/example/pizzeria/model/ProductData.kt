package com.example.pizzeria.model

data class ProductData(
    var productID: String? = "",
    var productName: String? = "",
    var productType: String? = "",
    var productPrice: Double? = 0.0,
    var productDescription: String? = "",
    var productImage: String? = "",

    ){
    constructor() : this(
        productID = "",
        productName = "",
        productType = "",
        productPrice = 0.0,
        productDescription = "",
        productImage = ""
    )
}