package com.andika.architecturecomponent.core.di

import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavouriteModule {

    fun movieInteractors(): MovieInteractors
}