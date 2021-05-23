package com.andika.architecturecomponent.business.interactors

import com.andika.architecturecomponent.business.data.local.LocalDataSource
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.domain.utils.AppConstant.LATEST_TV
import com.andika.architecturecomponent.business.domain.utils.AppConstant.NOW_PLAYING_MOVIES
import com.andika.architecturecomponent.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.andika.architecturecomponent.business.domain.utils.AppConstant.POPULAR_TV
import com.andika.architecturecomponent.business.domain.utils.AppConstant.UPCOMING_MOVIES
import com.andika.architecturecomponent.business.domain.utils.Helper
import com.andika.architecturecomponent.business.domain.utils.toMovie
import com.andika.architecturecomponent.business.domain.utils.toTV
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class FakeMovieInteractors
constructor(
    private val networkDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getTopMovies() = flow {
        emit(DataState.Loading)
        try {
            val data = networkDataSource.getTopMovies(1)
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getNowPlayingMovies() = flow {
        emit(DataState.Loading)
        try {
            Helper.getMoviePager(networkDataSource, NOW_PLAYING_MOVIES).collect {
                val data = it
                emit(DataState.Success(data))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getUpcoming() = flow {
        emit(DataState.Loading)
        try {
            Helper.getMoviePager(networkDataSource, UPCOMING_MOVIES).collect {
                val data = it
                emit(DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getPopular() = flow {
        emit(DataState.Loading)
        try {
            Helper.getMoviePager(networkDataSource, POPULAR_MOVIES).collect {
                val data = it
                emit(DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getPopularTv() = flow {
        emit(DataState.Loading)
        try {
            Helper.getTVPager(networkDataSource, POPULAR_TV).collect {
                val data = it
                emit(DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getTopRatedTv(): Flow<DataState<RemoteTVs>> = flow {
        emit(DataState.Loading)
        try {
            val data = networkDataSource.getTopRatedTV()
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getLatestTV() = flow {
        emit(DataState.Loading)
        try {
            Helper.getTVPager(networkDataSource, LATEST_TV).collect {
                val data = it
                emit(DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

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

    fun getSelectedMovie(id: Int): Flow<DataState<RemoteMovie>> = flow {
        emit(DataState.Loading)
        try {
            val data = localDataSource.getSelectedMovie(id).toMovie()
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    fun getSelectedTV(id: Int): Flow<DataState<RemoteTV>> = flow {
        emit(DataState.Loading)
        try {
            val data = localDataSource.getSelectedTV(id).toTV()
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}