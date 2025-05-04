package com.example.restaurant_app.ui.theme

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SingleItemLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        // Make each item full width of RecyclerView
        lp?.width = width / 1 // Show exactly 1 item
        return true
    }
}
