package com.codingtroops.foodies.data.repository

import com.codingtroops.foodies.model.ResultWrapper
import com.codingtroops.foodies.model.response.FoodCategoriesResponse
import com.codingtroops.foodies.model.response.MealsResponse
import kotlinx.coroutines.flow.Flow

interface FoodMenuRepository {
    suspend fun getFoodCategories(): Flow<ResultWrapper<FoodCategoriesResponse>>
    suspend fun getMealsByCategory(categoryName: String): Flow<ResultWrapper<MealsResponse>>
}