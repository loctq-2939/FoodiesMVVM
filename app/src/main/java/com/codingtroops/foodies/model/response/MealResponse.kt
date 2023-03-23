package com.codingtroops.foodies.model.response

import com.codingtroops.foodies.model.FoodItem
import com.google.gson.annotations.SerializedName

data class MealsResponse(val meals: List<MealResponse>)

data class MealResponse(
    @SerializedName("idMeal") val id: String? = null,
    @SerializedName("strMeal") val name: String? = null,
    @SerializedName("strMealThumb") val thumbnailUrl: String? = null,
)

fun MealsResponse.mapMealsToItems(): List<FoodItem> {
    return this.meals.map { category ->
        FoodItem(
            id = category.id,
            name = category.name,
            thumbnailUrl = category.thumbnailUrl
        )
    }
}
