package com.andika.architecturecomponent.business.interactors

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andika.architecturecomponent.business.data.local.LocalDataSource
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovies
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.domain.utils.AppConstant.LATEST_TV
import com.andika.architecturecomponent.business.domain.utils.AppConstant.NOW_PLAYING_MOVIES
import com.andika.architecturecomponent.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.andika.architecturecomponent.business.domain.utils.AppConstant.POPULAR_TV
import com.andika.architecturecomponent.business.domain.utils.AppConstant.UPCOMING_MOVIES
import com.andika.architecturecomponent.business.domain.utils.EspressoIdlingResource
import com.andika.architecturecomponent.business.domain.utils.Helper
import com.andika.architecturecomponent.business.domain.utils.toMovie
import com.andika.architecturecomponent.business.domain.utils.toTV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class MovieInteractors
constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: RemoteDataSource
) {

    suspend fun insertSelectedTV(tv: LocalTV) {
        localDataSource.insertSelectedTV(tv)
    }

    suspend fun insertSelectedMovie(movie: LocalMovie) {
        localDataSource.insertSelectedMovie(movie)
    }

    suspend fun removeSelectedMovie(movie: LocalMovie) {
        localDataSource.removeSelectedMovie(movie)
    }

    suspend fun removeSelectedTV(tv: LocalTV) {
        localDataSource.removeSelectedTV(tv)
    }

    fun getAllFavouriteMovie(scope: CoroutineScope): Flow<DataState<PagingData<LocalMovie>>> =
        flow {
            emit(DataState.Loading)
            EspressoIdlingResource.increment()
            try {
                localDataSource.getAllFavMovies().cachedIn(scope).collect {
                    val data = it
                    emit(DataState.Success(data))
                    EspressoIdlingResource.decrement()
                }
            } catch (e: Exception) {
                emit(DataState.Error(e))
                EspressoIdlingResource.decrement()
            }
        }

    fun getAllFavouriteTV(scope: CoroutineScope): Flow<DataState<PagingData<LocalTV>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            localDataSource.getAllFavTV().cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getSelectedMovie(id: Int): Flow<DataState<RemoteMovie>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = localDataSource.getSelectedMovie(id).toMovie()
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getSelectedTV(id: Int): Flow<DataState<RemoteTV>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = localDataSource.getSelectedTV(id).toTV()
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getDetailTV(id: String): Flow<DataState<RemoteTV>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getDetailTV(id)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getDetailMovie(id: String): Flow<DataState<RemoteMovie>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getDetailMovie(id)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getSimilar(filename: Int): Flow<DataState<RemoteMovies>> = flow {
        EspressoIdlingResource.increment()
        emit(DataState.Loading)
        try {
            val data = networkDataSource.getSimilar(filename = filename)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getSimilarTV(filename: Int): Flow<DataState<RemoteTVs>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getSimilarTV(filename = filename)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getRecomendation(filename: Int): Flow<DataState<RemoteMovies>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getRecomendation(filename = filename)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getRecomendationTV(filename: Int): Flow<DataState<RemoteTVs>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getRecomendationTV(filename = filename)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getTopMovies(): Flow<DataState<RemoteMovies>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getTopMovies(1)
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getUpcoming(scope: CoroutineScope): Flow<DataState<PagingData<RemoteMovie>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, UPCOMING_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getNowPlaying(scope: CoroutineScope): Flow<DataState<PagingData<RemoteMovie>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, NOW_PLAYING_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getPopular(scope: CoroutineScope): Flow<DataState<PagingData<RemoteMovie>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getMoviePager(networkDataSource, POPULAR_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }


    fun getPopularTv(scope: CoroutineScope): Flow<DataState<PagingData<RemoteTV>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getTVPager(networkDataSource, POPULAR_TV).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getTopRatedTv(): Flow<DataState<RemoteTVs>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            val data = networkDataSource.getTopRatedTV()
            emit(DataState.Success(data))
            EspressoIdlingResource.decrement()
        } catch (e: Exception) {
            emit(DataState.Error(e))
            EspressoIdlingResource.decrement()
        }
    }

    fun getLatestTV(scope: CoroutineScope): Flow<DataState<PagingData<RemoteTV>>> = flow {
        emit(DataState.Loading)
        EspressoIdlingResource.increment()
        try {
            Helper.getTVPager(networkDataSource, LATEST_TV).cachedIn(scope).collect {
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