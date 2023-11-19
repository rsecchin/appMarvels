package com.example.appmarvels.framework.di

import com.example.appmarvels.framework.CharactersRepositoryImpl
import com.example.appmarvels.framework.remote.RetrofitCharactersDataSource
import com.example.appmarvels.framework.repository.CharacterRepository
import com.example.appmarvels.framework.repository.CharactersRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(repository: CharactersRepositoryImpl): CharacterRepository

    @Binds
    fun bindRemoteDataSource(
        dataSource: RetrofitCharactersDataSource
    ): CharactersRemoteDataSource
}