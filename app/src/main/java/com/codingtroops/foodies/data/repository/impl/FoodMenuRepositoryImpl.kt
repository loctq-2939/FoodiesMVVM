package com.codingtroops.foodies.data.repository.impl

import com.codingtroops.foodies.data.remote.api.BaseApiConfig
import com.codingtroops.foodies.data.remote.api.Service
import com.codingtroops.foodies.data.repository.FoodMenuRepository
import com.codingtroops.foodies.model.ResultWrapper
import com.codingtroops.foodies.model.response.FoodCategoriesResponse
import com.codingtroops.foodies.model.response.MealsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodMenuRepositoryImpl @Inject constructor(
    private val service: Service
) : FoodMenuRepository, BaseApiConfig() {
    override suspend fun getFoodCategories(): Flow<ResultWrapper<FoodCategoriesResponse>> {
        return safeApiCallFlow {
            service.getFoodCategories()
        }
    }

    override suspend fun getMealsByCategory(categoryName: String): Flow<ResultWrapper<MealsResponse>> {
        return safeApiCallFlow {
            service.getMealsByCategory(categoryName)
        }
    }
}