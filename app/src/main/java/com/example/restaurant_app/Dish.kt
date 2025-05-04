package com.example.restaurant_app

data class Dish(
    val name: String,
    val imageUrl: String,
    val price: Double,
    val rating: Float,
    var count: Int = 0 // For quantity added to cart
)
