package com.example.appmarvels.presentation.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.appmarvels.MainCoroutineRule
import com.example.appmarvels.framework.useCase.GetFavoritesUseCase
import com.example.appmarvels.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<FavoritesViewModel.UiState>

    private val characters = listOf(CharacterFactory().create(CharacterFactory.Hero.ThreeDMan))

    private lateinit var favoritesViewModel: FavoritesViewModel

    @Before
    fun setup() {
        favoritesViewModel = FavoritesViewModel(
            getFavoritesUseCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            state.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with not filled favorite list when get favorite returns empty`() =
        runTest {
            // Arrange
            whenever(getFavoritesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        emptyList()
                    )
                )

            // Action
            favoritesViewModel.getAll()

            // Assert
            verify(uiStateObserver).onChanged(
                isA<FavoritesViewModel.UiState.ShowEmpty>()
            )
        }

    @Test
    fun `should notify uiState with filled favorite list when get favorite returns`() =
        runTest {
            // Arrange
            whenever(getFavoritesUseCase.invoke(any()))
                .thenReturn(flowOf(characters))

            // Action
            favoritesViewModel.getAll()

            // Assert
            TestCase.assertEquals(1, characters.size)
        }
}