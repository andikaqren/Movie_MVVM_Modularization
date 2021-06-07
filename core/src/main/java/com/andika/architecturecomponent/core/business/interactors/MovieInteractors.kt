package com.andika.architecturecomponent.core.business.interactors

import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.andika.architecturecomponent.core.business.domain.repository.IMovieRepository
import com.andika.architecturecomponent.core.business.domain.state.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class MovieInteractors
constructor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override suspend fun insertSelectedTV(tv: TV) {
        movieRepository.insertSelectedTV(tv)
    }

    override suspend fun insertSelectedMovie(movie: Movie) {
        movieRepository.insertSelectedMovie(movie)
    }

    override suspend fun removeSelectedMovie(movie: Movie) {
        movieRepository.removeSelectedMovie(movie)
    }

    override suspend fun removeSelectedTV(tv: TV) {
        movieRepository.removeSelectedTV(tv)
    }

    override fun getAllFavouriteMovie(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> {
        return movieRepository.getAllFavouriteMovie(scope)
    }

    override fun getAllFavouriteTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> {
        return movieRepository.getAllFavouriteTV(scope)
    }

    override fun getSelectedMovie(id: Int): Flow<DataState<Movie>> {
        return movieRepository.getSelectedMovie(id)
    }

    override fun getSelectedTV(id: Int): Flow<DataState<TV>> {
        return movieRepository.getSelectedTV(id)
    }

    override fun getDetailTV(id: String): Flow<DataState<TV>> {
        return movieRepository.getDetailTV(id)
    }

    override fun getDetailMovie(id: String): Flow<DataState<Movie>> {
        return movieRepository.getDetailMovie(id)
    }

    override fun getSimilar(filename: Int): Flow<DataState<Movies>> {
        return movieRepository.getSimilar(filename)
    }

    override fun getSimilarTV(filename: Int): Flow<DataState<TVs>> {
        return movieRepository.getSimilarTV(filename)
    }

    override fun getRecomendation(filename: Int): Flow<DataState<Movies>> {
        return movieRepository.getRecomendation(filename)
    }

    override fun getRecomendationTV(filename: Int): Flow<DataState<TVs>> {
        return movieRepository.getRecomendationTV(filename)
    }

    override fun getTopMovies(): Flow<DataState<Movies>> {
        return movieRepository.getTopMovies()
    }

    override fun getUpcoming(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> {
        return movieRepository.getUpcoming(scope)
    }

    override fun getNowPlaying(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> {
        return movieRepository.getNowPlaying(scope)
    }

    override fun getPopular(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> {
        return movieRepository.getPopular(scope)
    }

    override fun getPopularTv(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> {
        return movieRepository.getPopularTv(scope)
    }

    override fun getTopRatedTv(): Flow<DataState<TVs>> {
        return movieRepository.getTopRatedTv()
    }

    override fun getLatestTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> {
        return movieRepository.getLatestTV(scope)
    }
}


