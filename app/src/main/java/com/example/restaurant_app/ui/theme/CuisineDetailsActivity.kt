package com.example.restaurant_app.ui.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant_app.Dish
import com.example.restaurant_app.R

class CuisineDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuisine_details)

        // Get cuisine name from intent
        val cuisineName = intent.getStringExtra("CUISINE_NAME") ?: "Unknown Cuisine"

        // Set the title with the cuisine name
        findViewById<TextView>(R.id.cuisineTitle).text = cuisineName

        // Sample dishes for demo (you would get these from an API or database)
        val dishes = when(cuisineName) {
            "Italian" -> getItalianDishes()
            "South Indian" -> getSouthIndianDishes()
            "Chinese" -> getChineseDishes()
            "North Indian" -> getNorthIndianDishes()
            "Mexican" -> getMexicanDishes()
            else -> listOf()
        }

        // Set up RecyclerView with grid layout
        val recyclerView = findViewById<RecyclerView>(R.id.dishesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns

        val adapter = CuisineDetailsDishAdapter(dishes) { dish ->
            // Add dish to cart
            CartManager.addToCart(dish.copy(count = 1))
        }
        recyclerView.adapter = adapter
        findViewById<Button>(R.id.cartButton).setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }




    // Sample dish data methods
    private fun getItalianDishes(): List<Dish> = listOf(
        Dish("Margherita Pizza", "https://images.unsplash.com/photo-1604068549290-dea0e4a305ca?w=400", 250.0, 4.8f),
        Dish("Pasta Carbonara", "https://images.unsplash.com/photo-1612874742237-6526221588e3?w=400", 220.0, 4.5f),
        Dish("Lasagna", "https://images.unsplash.com/photo-1619895092538-128341789043?w=400", 280.0, 4.7f),
        Dish("Tiramisu", "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400", 150.0, 4.6f)
    )

    private fun getSouthIndianDishes(): List<Dish> = listOf(
        Dish("Masala Dosa", "https://images.pexels.com/photos/20422138/pexels-photo-20422138/free-photo-of-triangular-bread-on-plate.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 120.0, 4.9f),
        Dish("Idli Sambar", "https://images.pexels.com/photos/20422128/pexels-photo-20422128/free-photo-of-meal-and-soup.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 80.0, 4.4f),
        Dish("Vada", "https://images.pexels.com/photos/20422135/pexels-photo-20422135/free-photo-of-meal-on-plate-and-sauces.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 60.0, 4.3f),
        Dish("Rava Upma", "https://images.pexels.com/photos/20408458/pexels-photo-20408458/free-photo-of-indian-round-dish-with-spices-and-sauces-on-the-side.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 90.0, 4.1f)
    )

    private fun getChineseDishes(): List<Dish> = listOf(
        Dish("Veg Manchurian", "https://images.pexels.com/photos/29631489/pexels-photo-29631489/free-photo-of-delicious-indo-chinese-cuisine-manchurian-dish.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 180.0, 4.5f),
        Dish("Hakka Noodles", "https://images.pexels.com/photos/2764905/pexels-photo-2764905.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 150.0, 4.2f),
        Dish("Fried Rice", "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400", 160.0, 4.4f)
    )

    private fun getNorthIndianDishes(): List<Dish> = listOf(
        Dish("Paneer Butter Masala", "https://t3.ftcdn.net/jpg/12/38/79/98/240_F_1238799806_fYv5viLmcYf1qhqG5tAqgyy7YGa3Oz1L.jpg", 220.0, 4.7f),
        Dish("Butter Naan", "https://images.pexels.com/photos/1117862/pexels-photo-1117862.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 40.0, 4.6f),
        Dish("Dal Makhani", "https://images.pexels.com/photos/12737916/pexels-photo-12737916.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 180.0, 4.5f)
    )

    private fun getMexicanDishes(): List<Dish> = listOf(
        Dish("Tacos", "https://images.unsplash.com/photo-1611250188496-e966043a0629?w=400", 200.0, 4.6f),
        Dish("Quesadilla", "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f?w=400", 180.0, 4.4f),
        Dish("Burrito", "https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 210.0, 4.5f)
    )
}
