package com.codingtroops.foodies.model.response

import com.codingtroops.foodies.model.FoodItem
import com.google.gson.annotations.SerializedName

data class FoodCategoriesResponse(val categories: List<FoodCategoryResponse>)

data class FoodCategoryResponse(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val thumbnailUrl: String,
    @SerializedName("strCategoryDescription") val description: String = ""
)

fun FoodCategoriesResponse.mapCategoriesToItems(): List<FoodItem> {
    return this.categories.map { category ->
        FoodItem(
            id = category.id,
            name = category.name,
            description = category.description,
            thumbnailUrl = category.thumbnailUrl
        )
    }
}