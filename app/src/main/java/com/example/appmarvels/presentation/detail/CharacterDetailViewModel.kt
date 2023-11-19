package com.example.appmarvels.presentation.detail

import androidx.lifecycle.ViewModel
import com.example.appmarvels.framework.useCase.AddFavoritesUseCase
import com.example.appmarvels.framework.useCase.CheckFavoriteUseCase
import com.example.appmarvels.framework.useCase.GetCharacterCategoriesUseCase
import com.example.appmarvels.framework.useCase.RemoveFavoritesUseCase
import com.example.appmarvels.framework.useCase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoritesUseCase: AddFavoritesUseCase,
    removeFavoritesUseCase: RemoveFavoritesUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        getCharacterCategoriesUseCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutinesDispatchers.main(),
        checkFavoriteUseCase,
        addFavoritesUseCase,
        removeFavoritesUseCase
    )
}