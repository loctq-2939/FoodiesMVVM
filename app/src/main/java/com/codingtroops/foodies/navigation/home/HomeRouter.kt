package com.codingtroops.foodies.navigation.home

import androidx.navigation.NavHostController
import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Route.FOOD_CATEGORIES_LIST

interface HomeRouter {
    fun foodCategoryDetailRoute(foodCategoryId: String)
}

class HomeRouterImpl(private val navController: NavHostController) : HomeRouter {

    override fun foodCategoryDetailRoute(foodCategoryId: String) {
        navController.navigate("${FOOD_CATEGORIES_LIST}/${foodCategoryId}") // ex:  food_categories_list/1
    }
}