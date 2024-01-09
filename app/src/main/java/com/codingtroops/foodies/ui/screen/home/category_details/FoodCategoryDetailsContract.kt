package com.codingtroops.foodies.ui.screen.home.category_details


import com.codingtroops.foodies.model.FoodItem

class FoodCategoryDetailsContract {
    data class State(
        val category: FoodItem?,
        val categoryFoodItems: List<FoodItem>,
    )
}