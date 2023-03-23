package com.codingtroops.foodies.navigation.home

import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Arg.FOOD_CATEGORY_ID
import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Route.FOOD_CATEGORIES_LIST

object NavigationHomeKeys {

    object Arg {
        const val FOOD_CATEGORY_ID = "foodCategoryName"
    }

    object Route {
        const val FOOD_CATEGORIES_LIST = "food_categories_list"
        const val FOOD_CATEGORY_DETAILS = "$FOOD_CATEGORIES_LIST/{$FOOD_CATEGORY_ID}"
        //
    }
}