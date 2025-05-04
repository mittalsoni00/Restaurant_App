package com.example.restaurant_app.ui.theme

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant_app.Cuisine
import com.example.restaurant_app.R

class CuisineAdapter(
    private var cuisines: List<Cuisine>,
    private val onCuisineClicked: (Cuisine) -> Unit
) : RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>() {

    override fun getItemCount(): Int = if (cuisines.isEmpty()) 0 else Int.MAX_VALUE // Infinite scroll

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cuisine_card, parent, false)
        return CuisineViewHolder(view)
    }

//    override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
//        val cuisine = cuisines[position % cuisines.size]
//        holder.bind(cuisine)
//        holder.itemView.setOnClickListener { onCuisineClicked(cuisine) }
//    }
    override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
    val cuisine = cuisines[position % cuisines.size]
    holder.bind(cuisine)
    holder.itemView.setOnClickListener {
        // Create intent to navigate to CuisineDetailsActivity
        val intent = Intent(holder.itemView.context, CuisineDetailsActivity::class.java)
        intent.putExtra("CUISINE_NAME", cuisine.name)
        holder.itemView.context.startActivity(intent)
    }
}


    fun updateData(newCuisines: List<Cuisine>) {
        cuisines = newCuisines
        notifyDataSetChanged()
    }

    class CuisineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.cuisineImage)
        private val nameTextView: TextView = itemView.findViewById(R.id.cuisineName)

        // Pulsating text effect
        fun bind(cuisine: Cuisine) {
            // Load image with Glide
            Glide.with(itemView.context)
                .load(cuisine.imageUrl)
                .centerCrop()
                .into(imageView)

            nameTextView.text = cuisine.name

            // Creating pulsating animation
            val scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(
                nameTextView,
                PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f, 1f)
            )
            scaleAnimation.duration = 1500
            scaleAnimation.repeatCount = ValueAnimator.INFINITE
            scaleAnimation.interpolator = AccelerateDecelerateInterpolator()
            scaleAnimation.start()
        }


        //bounce text effect
//        fun bind(cuisine: Cuisine) {
//            // Load image with Glide
//            Glide.with(itemView.context)
//                .load(cuisine.imageUrl)
//                .centerCrop()
//                .into(imageView)
//
//            // Set text and animate
//            nameTextView.text = cuisine.name
//            val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.text_bounce)
//            nameTextView.startAnimation(animation)
//        }
        // Add this function to your CuisineAdapter


        //Typewriter text effect
//        private fun animateText(textView: TextView, text: String) {
//            textView.text = ""
//            val handler = Handler(Looper.getMainLooper())
//
//            for (i in text.indices) {
//                handler.postDelayed({
//                    textView.text = text.substring(0, i+1)
//                }, 50 * i.toLong())
//            }
//        }
//
//        // In your bind method
//        fun bind(cuisine: Cuisine) {
//            // Load image
//            Glide.with(itemView.context)
//                .load(cuisine.imageUrl)
//                .centerCrop()
//                .into(imageView)
//
//            // Animate text
//            animateText(nameTextView, cuisine.name)
//        }
        //liked pulsating , keeping that




    }
}
