package com.codingtroops.foodies.ui.screen.home.categories

import com.codingtroops.foodies.model.FoodItem


class FoodCategoriesContract {

    data class State(
        val categories: List<FoodItem> = listOf(),
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}