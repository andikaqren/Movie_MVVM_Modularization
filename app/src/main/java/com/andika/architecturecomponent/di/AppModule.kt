package com.andika.architecturecomponent.di

import com.andika.architecturecomponent.business.data.local.LocalDataSource
import com.andika.architecturecomponent.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.business.interactors.MovieInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getInteractors(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MovieInteractors {
        return MovieInteractors(localDataSource, remoteDataSource)
    }
}