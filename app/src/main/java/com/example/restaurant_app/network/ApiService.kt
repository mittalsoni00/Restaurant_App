package com.example.restaurant_app.network

import com.example.restaurant_app.ui.theme.CuisineCategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("emulator/interview/get_item_list")
//    fun getItemList(): Call<CuisineCategoryResponse>

    fun getCuisineCategories(): Call<CuisineCategoryResponse>
}
