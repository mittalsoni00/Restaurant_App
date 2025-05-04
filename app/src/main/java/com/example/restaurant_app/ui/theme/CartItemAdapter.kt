package com.example.restaurant_app.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant_app.Dish
import com.example.restaurant_app.R

class CartItemAdapter(
    private var cartItems: MutableList<Dish>,
    private val onRemoveClicked: (Dish) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_row, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }
    fun updateData(newCartItems: List<Dish>) {
        cartItems.clear()
        cartItems.addAll(newCartItems)
        notifyDataSetChanged()
    }
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dishImage: ImageView = itemView.findViewById(R.id.cartDishImage)
        private val dishName: TextView = itemView.findViewById(R.id.cartDishName)
        private val dishPrice: TextView = itemView.findViewById(R.id.cartDishPrice)
        private val dishQuantity: TextView = itemView.findViewById(R.id.cartDishQuantity)
        private val removeButton: ImageButton = itemView.findViewById(R.id.removeButton)

        fun bind(dish: Dish) {
            Glide.with(itemView.context).load(dish.imageUrl).into(dishImage)
            dishName.text = dish.name
            dishPrice.text = "₹${dish.price}"
            dishQuantity.text = "Qty: ${dish.count}"

            // Calculate item total
            val itemTotal = dish.price * dish.count
            itemView.findViewById<TextView>(R.id.itemTotal).text = "₹%.2f".format(itemTotal)

            removeButton.setOnClickListener {
                onRemoveClicked(dish)
            }
        }
    }
}
