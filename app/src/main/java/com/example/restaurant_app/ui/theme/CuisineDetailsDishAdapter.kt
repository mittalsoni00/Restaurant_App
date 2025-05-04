package com.example.restaurant_app.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant_app.Dish
import com.example.restaurant_app.R

class CuisineDetailsDishAdapter(
    private val dishes: List<Dish>,
    private val onAddClicked: (Dish) -> Unit
) : RecyclerView.Adapter<CuisineDetailsDishAdapter.DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cuisine_dish, parent, false)
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

        fun bind(dish: Dish, position: Int) {
            // Load dish image
            Glide.with(itemView.context)
                .load(dish.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(dishImage)

            // Set dish details
            dishName.text = dish.name
            dishPrice.text = "₹${dish.price}"
            dishRating.text = "⭐ ${dish.rating}"
            addButton.text = "ADD (${dish.count})"

            // Setup add button click
            addButton.setOnClickListener {
                dish.count += 1
                addButton.text = "ADD (${dish.count})"
                onAddClicked(dish)
            }
        }
    }
}
