package com.example.appmarvels.framework.di


import com.example.appmarvels.framework.FavoritesRepositoryImpl
import com.example.appmarvels.framework.local.RoomFavoritesDataSource
import com.example.appmarvels.framework.repository.FavoritesLocalDataSource
import com.example.appmarvels.framework.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    fun bindFavoritesRepository(repository: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomFavoritesDataSource
    ): FavoritesLocalDataSource
}