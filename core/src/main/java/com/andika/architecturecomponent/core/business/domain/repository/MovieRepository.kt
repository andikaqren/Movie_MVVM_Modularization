package com.andika.architecturecomponent.core.business.domain.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.andika.architecturecomponent.core.business.data.local.LocalDataSource
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant
import com.andika.architecturecomponent.core.business.domain.utils.DataMapper
import com.andika.architecturecomponent.core.business.domain.utils.EspressoIdlingResource
import com.andika.architecturecomponent.core.business.domain.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class MovieRepository
constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: RemoteDataSource
) : IMovieRepository {
    override suspend fun insertSelectedTV(tv: TV) {
        localDataSource.insertSelectedTV(DataMapper.tvToLocalTV(tv))
    }

    override suspend fun insertSelectedMovie(movie: Movie) {
        localDataSource.insertSelectedMovie(DataMapper.movieToLocalMovie(movie))
    }

    override suspend fun removeSelectedMovie(movie: Movie) {
        localDataSource.removeSelectedMovie(DataMapper.movieToLocalMovie(movie))
    }

    override suspend fun removeSelectedTV(tv: TV) {
        localDataSource.removeSelectedTV(DataMapper.tvToLocalTV(tv))
    }

    override fun getAllFavouriteMovie(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> =
        flow {
            emit(DataState.Loading)
            EspressoIdlingResource.increment()
            try {
                localDataSource.getAllFavMovies().cachedIn(scope).collect { data ->
                    emit(DataState.Success(data.map { DataMapper.localMovieToMovie(it) }))
                    EspressoIdlingResource.decrement()
                }
            } catch (e: Exception) {
                emit(DataState.Error(e))
                EspressoIdlingResource.decrement()
            }
        }

    override fun getAllFavouriteTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            localDataSource.getAllFavTV().cachedIn(scope).collect { data ->
                emit(DataState.Success(data.map { DataMapper.localTVToTV(it) }))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getSelectedMovie(id: Int): Flow<DataState<Movie>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.localMovieToMovie(localDataSource.getSelectedMovie(id))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    override fun getSelectedTV(id: Int): Flow<DataState<TV>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.localTVToTV(localDataSource.getSelectedTV(id))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getDetailTV(id: String): Flow<DataState<TV>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteTVToTV(networkDataSource.getDetailTV(id))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getDetailMovie(id: String): Flow<DataState<Movie>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteMovieToMovie(networkDataSource.getDetailMovie(id))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    override fun getSimilar(filename: Int): Flow<DataState<Movies>> = flow {
        EspressoIdlingResource.increment()
        emit(DataState.Loading)
        try {
            val data =
                DataMapper.remoteMoviesToMovies(networkDataSource.getSimilar(filename = filename))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override  fun getSimilarTV(filename: Int): Flow<DataState<TVs>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data =
                DataMapper.remoteTVsToTVs(networkDataSource.getSimilarTV(filename = filename))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getRecomendation(filename: Int): Flow<DataState<Movies>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data =
                DataMapper.remoteMoviesToMovies(networkDataSource.getRecomendation(filename = filename))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getRecomendationTV(filename: Int): Flow<DataState<TVs>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data =
                DataMapper.remoteTVsToTVs(networkDataSource.getRecomendationTV(filename = filename))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getTopMovies(): Flow<DataState<Movies>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteMoviesToMovies(networkDataSource.getTopMovies(1))
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    override fun getUpcoming(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, AppConstant.UPCOMING_MOVIES).cachedIn(scope)
                .collect {
                    val data = it
                    emit(DataState.Success(data))
                    EspressoIdlingResource.decrement()
                }

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getNowPlaying(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, AppConstant.NOW_PLAYING_MOVIES).cachedIn(scope)
                .collect {
                    val data = it
                    emit(DataState.Success(data))
                    EspressoIdlingResource.decrement()
                }

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    override fun getPopular(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, AppConstant.POPULAR_MOVIES).cachedIn(scope)
                .collect {
                    val data = it
                    emit(DataState.Success(data))
                    EspressoIdlingResource.decrement()
                }

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    override fun getPopularTv(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getTVPager(networkDataSource, AppConstant.POPULAR_TV).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getTopRatedTv(): Flow<DataState<TVs>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = DataMapper.remoteTVsToTVs(networkDataSource.getTopRatedTV())
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getLatestTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getTVPager(networkDataSource, AppConstant.LATEST_TV).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

}