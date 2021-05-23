package com.andika.architecturecomponent.core.business.interactors

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andika.architecturecomponent.core.business.data.local.LocalDataSource
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.domain.model.*
import com.andika.architecturecomponent.core.business.data.local.model.*
import com.andika.architecturecomponent.core.business.data.remote.model.*
import com.andika.architecturecomponent.core.business.domain.utils.DataMapper
import com.andika.architecturecomponent.core.business.domain.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieInteractors
constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: RemoteDataSource
) {

    suspend fun insertSelectedTV(tv: TV) {
        localDataSource.insertSelectedTV(DataMapper.tvToLocalTV(tv))
    }

    suspend fun insertSelectedMovie(movie: Movie) {
        localDataSource.insertSelectedMovie(DataMapper.movieToLocalMovie(movie))
    }

    suspend fun removeSelectedMovie(movie: Movie) {
        localDataSource.removeSelectedMovie(DataMapper.movieToLocalMovie(movie))
    }

    suspend fun removeSelectedTV(tv: TV) {
        localDataSource.removeSelectedTV(DataMapper.tvToLocalTV(tv))
    }

    fun getAllFavouriteMovie(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<Movie>>> =
        flow {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            EspressoIdlingResource.increment()
            try {
                localDataSource.getAllFavMovies().cachedIn(scope).collect {
                    val data = it
                    emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data.map { DataMapper.localMovieToMovie(it) }))
                    EspressoIdlingResource.decrement()
                }
            } catch (e: Exception) {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
                EspressoIdlingResource.decrement()
            }
        }

    fun getAllFavouriteTV(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<TV>>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            localDataSource.getAllFavTV().cachedIn(scope).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data.map { DataMapper.localTVToTV(it) }))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getSelectedMovie(id: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<Movie>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.localMovieToMovie(localDataSource.getSelectedMovie(id))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getSelectedTV(id: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<TV>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.localTVToTV(localDataSource.getSelectedTV(id))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getDetailTV(id: String): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<TV>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteTVToTV(networkDataSource.getDetailTV(id))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getDetailMovie(id: String): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<Movie>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteMovieToMovie(networkDataSource.getDetailMovie(id))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getSimilar(filename: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<Movies>> = flow {
        EspressoIdlingResource.increment()
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            val data =
                DataMapper.remoteMoviesToMovies(networkDataSource.getSimilar(filename = filename))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getSimilarTV(filename: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<TVs>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data =
                DataMapper.remoteTVsToTVs(networkDataSource.getSimilarTV(filename = filename))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getRecomendation(filename: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<Movies>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data =
                DataMapper.remoteMoviesToMovies(networkDataSource.getRecomendation(filename = filename))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getRecomendationTV(filename: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<TVs>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data =
                DataMapper.remoteTVsToTVs(networkDataSource.getRecomendationTV(filename = filename))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getTopMovies(): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<Movies>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteMoviesToMovies(networkDataSource.getTopMovies(1))
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()

        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getUpcoming(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<Movie>>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, UPCOMING_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
                EspressoIdlingResource.decrement()
            }

        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getNowPlaying(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<Movie>>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, NOW_PLAYING_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
                EspressoIdlingResource.decrement()
            }

        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getPopular(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<Movie>>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, POPULAR_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
                EspressoIdlingResource.decrement()
            }

        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getPopularTv(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<TV>>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getTVPager(networkDataSource, POPULAR_TV).cachedIn(scope).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getTopRatedTv(): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<TVs>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteTVsToTVs(networkDataSource.getTopRatedTV())
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getLatestTV(scope: CoroutineScope): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<TV>>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getTVPager(networkDataSource, LATEST_TV).cachedIn(scope).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


}