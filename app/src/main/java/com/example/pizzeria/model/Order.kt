package com.example.pizzeria.model
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Order(
    var id: String ?= "",
    val custumerid: String? = "",
    val custumerName: String? = "",
    val custumerPhone: String? = "",
    val custumerAdd: String? = "",
    val date: Date?=null,
    val total: Double?=0.0,
    val note: String?="",
    var status: String?,
    var items: List<OrderItem>?=null
) {
    constructor() : this("", "", "", "", "", null, 0.0, "", null, null)
}
@Serializable
data class OrderItem(
    val id: String?="",
    val name: String?="",
    val description: String?="",
    val image: String?="",
    val price: Double?=0.0,
    val quantity: Int?=0,

)