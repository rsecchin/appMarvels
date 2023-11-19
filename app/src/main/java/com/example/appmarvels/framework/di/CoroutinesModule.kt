package com.example.appmarvels.framework.di

import com.example.appmarvels.framework.useCase.base.AppCoroutinesDispatchers
import com.example.appmarvels.framework.useCase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}