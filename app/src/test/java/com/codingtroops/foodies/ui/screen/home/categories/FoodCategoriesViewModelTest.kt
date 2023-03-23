package com.codingtroops.foodies.ui.screen.home.categories

import androidx.lifecycle.Observer
import com.codingtroops.foodies.BaseViewModelTest
import com.codingtroops.foodies.data.repository.FoodMenuRepository
import com.codingtroops.foodies.model.ResultWrapper
import com.codingtroops.foodies.model.response.FoodCategoriesResponse
import com.codingtroops.foodies.model.response.FoodCategoryResponse
import com.codingtroops.foodies.model.response.mapCategoriesToItems
import io.mockk.called
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FoodCategoriesViewModelTest : BaseViewModelTest<FoodCategoriesViewModel>() {

    @MockK
    private lateinit var repository: FoodMenuRepository

    private val isErrorObserver: Observer<Boolean> = mockk(relaxed = true)
    private val foodCategoriesContract: Observer<FoodCategoriesContract> = mockk(relaxed = true)

    override fun setup() {
        super.setup()
        viewModel = FoodCategoriesViewModel(
            repository
        )
    }

    @Test
    fun getFoodCategories_Success() {
        val data = FoodCategoriesResponse(
            listOf(
                FoodCategoryResponse(id = "1", name = "name", thumbnailUrl = "")
            )
        )

        coEvery { repository.getFoodCategories() } returns flow {
            emit(ResultWrapper.success(data))
        }

        //When
        registerObserver(viewModel)
        viewModel.getFoodCategories()

        //Then
        verify {
            isErrorObserver wasNot called
        }

        assertEquals(data.mapCategoriesToItems(), viewModel.state.categories)
    }

    @Test
    fun getFoodCategories_Fail() {
        coEvery { repository.getFoodCategories() } returns flow {
            emit(ResultWrapper.error(Throwable()))
        }

        //When
        registerObserver(viewModel)
        viewModel.getFoodCategories()

        //Then
        verify {
            isErrorObserver.onChanged(true)
        }

        assertEquals(listOf(), viewModel.state.categories)
    }

    private fun registerObserver(viewModel: FoodCategoriesViewModel) {
        viewModel.apply {
            isError.observeForever(isErrorObserver)
        }
    }
}