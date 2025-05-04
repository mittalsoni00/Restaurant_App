package com.example.restaurant_app.ui.theme
import android.widget.ToggleButton
import java.util.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.restaurant_app.utils.isNetworkAvailable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant_app.Cuisine
import com.example.restaurant_app.Dish
import com.example.restaurant_app.R
import com.example.restaurant_app.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CuisineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cuisine RecyclerView setup
        recyclerView = findViewById(R.id.cuisineRecyclerView)

        // Setup RecyclerView to show one card at a time centered
        val singleItemLayoutManager = SingleItemLayoutManager(this)
        recyclerView.layoutManager = singleItemLayoutManager

        // Add snapping behavior to center each card
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val dummyCuisines = listOf(
            Cuisine("North Indian", "https://uat-static.onebanc.ai/picture/ob_cuisine_north_indian.webp"),
            Cuisine("Chinese", "https://uat-static.onebanc.ai/picture/ob_cuisine_chinese.webp"),
            Cuisine("Mexican", "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=400&q=80"),
            Cuisine("South Indian", "https://img.freepik.com/free-photo/top-view-indian-food-assortment_23-2148742201.jpg?w=400"),
            Cuisine("Italian", "https://images.unsplash.com/photo-1612874742237-6526221588e3?w=400")
        )

        adapter = CuisineAdapter(dummyCuisines) { cuisine ->
            Toast.makeText(this, cuisine.name, Toast.LENGTH_SHORT).show()
            // TODO: Navigate to next screen
        }
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)

        // Fetch cuisines from API and update adapter when data arrives
        ApiClient.apiService.getCuisineCategories().enqueue(object : Callback<CuisineCategoryResponse> {
            override fun onResponse(
                call: Call<CuisineCategoryResponse>,
                response: Response<CuisineCategoryResponse>
            ) {
                Log.d("API_RESPONSE", response.body().toString())
                if (response.isSuccessful && response.body() != null) {
                    val cuisines = response.body()!!.categories
                    adapter.updateData(cuisines)
                    recyclerView.scrollToPosition(Int.MAX_VALUE / 2)
                } else {
                    Toast.makeText(this@MainActivity, "No data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CuisineCategoryResponse>, t: Throwable) {
                Log.e("API_ERROR", t.message ?: "Unknown error")
                Toast.makeText(this@MainActivity, "API Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

        // Top 3 Famous Dishes RecyclerView
        val topDishes = mutableListOf(
            Dish("Paneer Butter Masala", "https://t3.ftcdn.net/jpg/12/38/79/98/240_F_1238799806_fYv5viLmcYf1qhqG5tAqgyy7YGa3Oz1L.jpg", 220.0, 4.7f),
            Dish("Veg Manchurian", "https://images.pexels.com/photos/29631489/pexels-photo-29631489/free-photo-of-delicious-indo-chinese-cuisine-manchurian-dish.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", 180.0, 4.5f),
            Dish("Margherita Pizza", "https://as2.ftcdn.net/v2/jpg/10/64/58/43/1000_F_1064584312_P0aoF5bIDsqAZ52kwnc4y63aq30p3p9F.jpg", 250.0, 4.8f)
        )
        val dishAdapter = DishAdapter(topDishes) { dish ->
            // Optionally handle add to cart
        }
        val dishesRecyclerView = findViewById<RecyclerView>(R.id.dishesRecyclerView)
        dishesRecyclerView.adapter = dishAdapter
        dishesRecyclerView.layoutManager = LinearLayoutManager(this)

        // --- Cart Button Code ---
        findViewById<Button>(R.id.cartButton).setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val languageToggle = findViewById<ToggleButton>(R.id.languageToggle)
        languageToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setLocale("en") // English
            } else {
                setLocale("hi") // Hindi
            }
        }
    }

    private fun setLocale(languageCode: String) {
        // Use AndroidX API for safe locale switching
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
    override fun onResume() {
        super.onResume()
        val statusView = findViewById<TextView>(R.id.networkStatus)
        if (isNetworkAvailable(this)) {
            statusView.text = ""
            statusView.setTextColor(getColor(android.R.color.holo_green_dark))
        } else {
            statusView.text = ""
            statusView.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }

}
