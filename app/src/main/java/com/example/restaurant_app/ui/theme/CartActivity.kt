package com.example.restaurant_app.ui.theme

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant_app.Dish
import com.example.restaurant_app.R
import com.example.restaurant_app.ui.theme.CartItemAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Set up RecyclerView
        val cartRecyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get cart items and display them
//        val cartItems = CartManager.getCartItems()
        cartAdapter = CartItemAdapter(CartManager.getCartItems().toMutableList()) { dish ->
            // Handle remove from cart
            CartManager.removeFromCart(dish)
            updateCartUI()
            cartAdapter.updateData(CartManager.getCartItems())
        }
        cartRecyclerView.adapter = cartAdapter

        // Set up place order button
        findViewById<Button>(R.id.placeOrderButton).setOnClickListener {
            Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_LONG).show()
            CartManager.clearCart()
            finish() // Go back to main screen
        }

        // Initialize the UI with current cart values
        updateCartUI()
    }
    override fun onResume() {
        super.onResume()
        // Refresh cart every time activity is resumed
        cartAdapter.updateData(CartManager.getCartItems())
        updateCartUI()
    }
    private fun updateCartUI() {
        // Always get the latest cart items
        val cartItems = CartManager.getCartItems()

        val netTotal = CartManager.calculateNetTotal()
        val cgst = CartManager.calculateCGST()
        val sgst = CartManager.calculateSGST()
        val grandTotal = CartManager.calculateGrandTotal()

        findViewById<TextView>(R.id.netTotalTextView).text = "₹%.2f".format(netTotal)
        findViewById<TextView>(R.id.cgstTextView).text = "₹%.2f".format(cgst)
        findViewById<TextView>(R.id.sgstTextView).text = "₹%.2f".format(sgst)
        findViewById<TextView>(R.id.grandTotalTextView).text = "₹%.2f".format(grandTotal)

        // Now this works because cartItems is in scope
        findViewById<Button>(R.id.placeOrderButton).isEnabled = cartItems.isNotEmpty()
    }

}

