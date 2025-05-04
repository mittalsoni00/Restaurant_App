package com.example.restaurant_app.ui.theme

import com.example.restaurant_app.Dish

// CartManager.kt
object CartManager {
    private val cartItems = mutableListOf<Dish>()

    fun addToCart(dish: Dish) {
        // Check if item already exists, if so just increase count
        val existingDish = cartItems.find { it.name == dish.name }
        if (existingDish != null) {
            existingDish.count += dish.count
        } else {
            cartItems.add(dish.copy()) //making copy to avoid ref. issues
        }
    }

    fun removeOneFromCart(dish: Dish) {
        val existingDish = cartItems.find { it.name == dish.name }
        if (existingDish != null && existingDish.count > 1) {
            existingDish.count -= 1
        } else if (existingDish != null) {
            cartItems.remove(existingDish)
        }
    }


    fun getCartItems(): List<Dish> = cartItems

    fun clearCart() {
        cartItems.clear()
    }

    fun removeFromCart(dish: Dish) {
        val itemToRemove = cartItems.find { it.name == dish.name }
        if (itemToRemove != null) {
            cartItems.remove(itemToRemove)
        }
    }

    fun calculateNetTotal(): Double {
        return cartItems.sumOf { it.price * it.count }
    }

    fun calculateCGST(): Double {
        return calculateNetTotal() * 0.025 // 2.5%
    }

    fun calculateSGST(): Double {
        return calculateNetTotal() * 0.025 // 2.5%
    }

    fun calculateGrandTotal(): Double {
        return calculateNetTotal() + calculateCGST() + calculateSGST()
    }
}
