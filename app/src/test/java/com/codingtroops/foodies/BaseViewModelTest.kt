package com.codingtroops.foodies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseViewModelTest<T : ViewModel> {

    protected lateinit var viewModel: T
    protected var context = mockk<Context>(relaxed = true)

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @BeforeTest
    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

}