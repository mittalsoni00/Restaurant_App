package com.example.restaurant_app.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant_app.Dish
import com.example.restaurant_app.R

class DishAdapter(
    private val dishes: MutableList<Dish>,
    private val onAddClicked: (Dish) -> Unit
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish_tile, parent, false)
        return DishViewHolder(view)
    }

    override fun getItemCount() = dishes.size

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(dishes[position], position)
    }

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dishImage: ImageView = itemView.findViewById(R.id.dishImage)
        private val dishName: TextView = itemView.findViewById(R.id.dishName)
        private val dishPrice: TextView = itemView.findViewById(R.id.dishPrice)
        private val dishRating: TextView = itemView.findViewById(R.id.dishRating)
        private val addButton: Button = itemView.findViewById(R.id.addButton)
        private val counterLayout: LinearLayout = itemView.findViewById(R.id.counterLayout)
        private val minusButton: Button = itemView.findViewById(R.id.minusButton)
        private val plusButton: Button = itemView.findViewById(R.id.plusButton)
        private val countText: TextView = itemView.findViewById(R.id.countText)

        fun bind(dish: Dish, position: Int) {
            // Bind dish details
            Glide.with(itemView.context).load(dish.imageUrl).into(dishImage)
            dishName.text = dish.name
            dishPrice.text = "₹${dish.price}"
            dishRating.text = "⭐ ${dish.rating}"

            // Always set the count text regardless of visibility!
            countText.text = dish.count.toString()

            // Show/hide appropriate controls based on count
            if (dish.count == 0) {
                addButton.visibility = View.VISIBLE
                counterLayout.visibility = View.GONE
            } else {
                addButton.visibility = View.GONE
                counterLayout.visibility = View.VISIBLE
            }

            // Set click listeners
            addButton.setOnClickListener {
                dish.count = 1
                notifyItemChanged(position)
                // Add to cart
                CartManager.addToCart(dish)
                onAddClicked(dish)
            }
            //when plus button is clicked
            plusButton.setOnClickListener {
                dish.count += 1
//                countText.text = dishes[position].count.toString()
                notifyItemChanged(position)
                // Update cart
                CartManager.addToCart(dish.copy(count = 1))
                onAddClicked(dish)
            }

            minusButton.setOnClickListener {
                if (dish.count > 1) {
                    dish.count -= 1
                    notifyItemChanged(position)
                    // Update cart
                    CartManager.removeOneFromCart(dish)
                    onAddClicked(dish)
                } else if (dish.count == 1) {
                    dish.count = 0
                    notifyItemChanged(position)
                    // Remove from cart
                    CartManager.removeFromCart(dish.copy(count = 0))
                    onAddClicked(dish)
                }
            }
        }
    }
}
