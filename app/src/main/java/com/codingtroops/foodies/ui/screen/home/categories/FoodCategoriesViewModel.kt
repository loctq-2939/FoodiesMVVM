package com.codingtroops.foodies.ui.screen.home.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.codingtroops.foodies.base.BaseViewModel
import com.codingtroops.foodies.data.repository.FoodMenuRepository
import com.codingtroops.foodies.model.handleResponse
import com.codingtroops.foodies.model.response.mapCategoriesToItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodCategoriesViewModel @Inject constructor(
    private val repository: FoodMenuRepository
) : BaseViewModel() {

    var state by mutableStateOf(
        FoodCategoriesContract.State(
            categories = listOf(),
            isLoading = true
        )
    )
        private set

    var effects = Channel<FoodCategoriesContract.Effect>(UNLIMITED)
        private set

    init {
        getFoodCategories()
    }

    fun getFoodCategories() {
        viewModelScope.launch {
            setLoading(true)
            repository.getFoodCategories().handleResponse(
                onSuccess = {
                    state = state.copy(categories = it.mapCategoriesToItems(), isLoading = false)
                    viewModelScope.launch {
                        effects.send(FoodCategoriesContract.Effect.DataWasLoaded)
                    }
                },
                onCompletion = {
                    setLoading(false)
                },
                onError = {
                    onError(it)
                }
            )
        }
    }
}



