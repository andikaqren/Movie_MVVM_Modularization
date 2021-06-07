package com.andika.architecturecomponent.core.business.domain.repository

import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun insertSelectedTV(tv: TV)

    suspend fun insertSelectedMovie(movie: Movie)

    suspend fun removeSelectedMovie(movie: Movie)

    suspend fun removeSelectedTV(tv: TV)

    fun getAllFavouriteMovie(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>>

    fun getAllFavouriteTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>>

    fun getSelectedMovie(id: Int): Flow<DataState<Movie>>

    fun getSelectedTV(id: Int): Flow<DataState<TV>>

    fun getDetailTV(id: String): Flow<DataState<TV>>

    fun getDetailMovie(id: String): Flow<DataState<Movie>>

    fun getSimilar(filename: Int): Flow<DataState<Movies>>

    fun getSimilarTV(filename: Int): Flow<DataState<TVs>>

    fun getRecomendation(filename: Int): Flow<DataState<Movies>>

    fun getRecomendationTV(filename: Int): Flow<DataState<TVs>>

    fun getTopMovies(): Flow<DataState<Movies>>

    fun getUpcoming(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>>

    fun getNowPlaying(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>>

    fun getPopular(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>>

    fun getPopularTv(scope: CoroutineScope): Flow<DataState<PagingData<TV>>>

    fun getTopRatedTv(): Flow<DataState<TVs>>

    fun getLatestTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>>


}