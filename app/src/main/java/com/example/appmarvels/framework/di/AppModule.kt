package com.example.appmarvels.framework.di

import com.example.appmarvels.framework.imageLoader.GlideImageLoader
import com.example.appmarvels.framework.imageLoader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}