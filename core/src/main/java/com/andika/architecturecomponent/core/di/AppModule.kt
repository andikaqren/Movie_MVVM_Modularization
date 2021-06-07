package com.andika.architecturecomponent.core.di

import com.andika.architecturecomponent.core.business.data.local.LocalDataSource
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.domain.repository.IMovieRepository
import com.andika.architecturecomponent.core.business.domain.repository.MovieRepository
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import com.andika.architecturecomponent.core.business.interactors.MovieUseCase
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
    fun getRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): IMovieRepository {
        return MovieRepository(localDataSource, remoteDataSource)
    }

    @Singleton
    @Provides
    fun getInteractors(
        repository: IMovieRepository
    ): MovieUseCase {
        return MovieInteractors(repository)
    }
}