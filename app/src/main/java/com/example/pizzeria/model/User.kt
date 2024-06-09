package com.example.pizzeria.model

data class User(
    var userID: String?="",
    var fullName: String?="",
    var phoneNumber: String?="",
    var email: String?="",
    var password: String?="",
    var address: String? = null,
    var sex: String?=null,
    var birthday: String? = null,
    var image: String?="",
    var role: String?=""
)
