package com.codingtroops.foodies.data.remote.api

import com.codingtroops.foodies.model.response.FoodCategoriesResponse
import com.codingtroops.foodies.model.response.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("categories.php")
    suspend fun getFoodCategories(): FoodCategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName: String): MealsResponse
}