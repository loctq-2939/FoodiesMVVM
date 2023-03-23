package com.codingtroops.foodies.navigation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codingtroops.foodies.base.AppComposable
import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Arg.FOOD_CATEGORY_ID
import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Route.FOOD_CATEGORIES_LIST
import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Route.FOOD_CATEGORY_DETAILS
import com.codingtroops.foodies.ui.screen.home.categories.FoodCategoriesScreen
import com.codingtroops.foodies.ui.screen.home.categories.FoodCategoriesViewModel
import com.codingtroops.foodies.ui.screen.home.category_details.FoodCategoryDetailsScreen
import com.codingtroops.foodies.ui.screen.home.category_details.FoodCategoryDetailsViewModel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun HomeTab() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = FOOD_CATEGORIES_LIST) {
        composable(route = FOOD_CATEGORIES_LIST) {
            FoodCategoriesDestination(navController)
        }
        composable(
            route = FOOD_CATEGORY_DETAILS,
            arguments = listOf(navArgument(FOOD_CATEGORY_ID) {
                type = NavType.StringType
            })
        ) {
            FoodCategoryDetailsDestination(navController)
        }
    }
}

@Composable
fun FoodCategoriesDestination(
    navController: NavHostController,
    viewModel: FoodCategoriesViewModel = hiltViewModel()
) {
    val homeRouter by lazy { HomeRouterImpl(navController) }
    AppComposable(viewModel = viewModel) {
        FoodCategoriesScreen(
            state = viewModel.state,
            effectFlow = viewModel.effects.receiveAsFlow(),
            onNavigationRequested = { itemId ->
                homeRouter.foodCategoryDetailRoute(itemId)
            })
    }
}

@Composable
fun FoodCategoryDetailsDestination(
    navController: NavHostController,
    viewModel: FoodCategoryDetailsViewModel = hiltViewModel()
) {
    AppComposable(viewModel = viewModel) {
        FoodCategoryDetailsScreen(viewModel.state) {
            navController.popBackStack()
        }
    }
}