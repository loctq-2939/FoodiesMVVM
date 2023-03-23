package com.codingtroops.foodies.ui.screen.home.category_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.codingtroops.foodies.base.BaseViewModel
import com.codingtroops.foodies.data.repository.FoodMenuRepository
import com.codingtroops.foodies.model.handleResponse
import com.codingtroops.foodies.model.response.mapCategoriesToItems
import com.codingtroops.foodies.model.response.mapMealsToItems
import com.codingtroops.foodies.navigation.home.NavigationHomeKeys.Arg.FOOD_CATEGORY_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodCategoryDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val repository: FoodMenuRepository
) : BaseViewModel() {

    var state by mutableStateOf(
        FoodCategoryDetailsContract.State(
            null, listOf()
        )
    )
        private set

    init {
        val categoryId = stateHandle.get<String?>(FOOD_CATEGORY_ID)
        categoryId?.let {
            getFoodCategories(it)
        }
    }

    private fun getFoodCategories(categoryId: String) {
        viewModelScope.launch {
            setLoading(true)
            repository.getFoodCategories().handleResponse(
                onSuccess = {
                    val category = it.mapCategoriesToItems().first { it.id == categoryId }
                    state = state.copy(category = category)
                    category.name?.let { name -> getMealsByCategory(name) }
                }
            )
        }
    }

    private fun getMealsByCategory(categoryName: String) {
        viewModelScope.launch {
            repository.getMealsByCategory(categoryName).handleResponse(
                onSuccess = {
                    state = state.copy(categoryFoodItems = it.mapMealsToItems())
                },
                onCompletion = {
                    setLoading(false)
                }
            )
        }
    }

}
