package com.example.appmarvels.framework.di

import com.example.appmarvels.framework.useCase.AddFavoritesUseCase
import com.example.appmarvels.framework.useCase.AddFavoritesUseCaseImpl
import com.example.appmarvels.framework.useCase.CheckFavoriteUseCase
import com.example.appmarvels.framework.useCase.CheckFavoriteUseCaseImpl
import com.example.appmarvels.framework.useCase.GetCharacterCategoriesUseCase
import com.example.appmarvels.framework.useCase.GetCharacterCategoriesUseCaseImpl
import com.example.appmarvels.framework.useCase.GetCharactersUseCase
import com.example.appmarvels.framework.useCase.GetCharactersUseCaseImpl
import com.example.appmarvels.framework.useCase.GetFavoritesUseCase
import com.example.appmarvels.framework.useCase.GetFavoritesUseCaseImpl
import com.example.appmarvels.framework.useCase.RemoveFavoritesUseCase
import com.example.appmarvels.framework.useCase.RemoveFavoritesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharacterUseCase( useCase: GetCharactersUseCaseImpl) : GetCharactersUseCase

    @Binds
    fun bindGetComicUseCase(
        useCase: GetCharacterCategoriesUseCaseImpl
    ) : GetCharacterCategoriesUseCase

    @Binds
    fun bindCheckFavoriteUseCase(useCase: CheckFavoriteUseCaseImpl): CheckFavoriteUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase: AddFavoritesUseCaseImpl): AddFavoritesUseCase

    @Binds
    fun bindRemoveFavoriteUseCase(useCase: RemoveFavoritesUseCaseImpl): RemoveFavoritesUseCase

    @Binds
    fun bindGetFavoritesUseCase(useCase: GetFavoritesUseCaseImpl): GetFavoritesUseCase
}