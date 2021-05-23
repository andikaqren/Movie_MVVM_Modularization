package com.andika.architecturecomponent.business.interactors

import com.andika.architecturecomponent.core.business.data.local.LocalDataSource
import com.andika.architecturecomponent.core.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.core.business.data.local.model.LocalTV
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeMovieInteractors
constructor(
    private val networkDataSource: com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource,
    private val localDataSource: com.andika.architecturecomponent.core.business.data.local.LocalDataSource
) {

    fun getTopMovies() = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            val data = networkDataSource.getTopMovies(1)
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    fun getNowPlayingMovies() = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            com.andika.architecturecomponent.core.business.domain.utils.Helper.getMoviePager(networkDataSource, NOW_PLAYING_MOVIES).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            }

        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    fun getUpcoming() = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            com.andika.architecturecomponent.core.business.domain.utils.Helper.getMoviePager(networkDataSource, UPCOMING_MOVIES).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    fun getPopular() = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            com.andika.architecturecomponent.core.business.domain.utils.Helper.getMoviePager(networkDataSource, POPULAR_MOVIES).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    fun getPopularTv() = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            com.andika.architecturecomponent.core.business.domain.utils.Helper.getTVPager(networkDataSource, POPULAR_TV).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    fun getTopRatedTv(): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            val data = networkDataSource.getTopRatedTV()
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    fun getLatestTV() = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            com.andika.architecturecomponent.core.business.domain.utils.Helper.getTVPager(networkDataSource, LATEST_TV).collect {
                val data = it
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }

    suspend fun insertSelectedTV(tv: com.andika.architecturecomponent.core.business.data.local.model.LocalTV) {
        localDataSource.insertSelectedTV(tv)
    }

    suspend fun insertSelectedMovie(movie: com.andika.architecturecomponent.core.business.data.local.model.LocalMovie) {
        localDataSource.insertSelectedMovie(movie)
    }

    suspend fun removeSelectedMovie(movie: com.andika.architecturecomponent.core.business.data.local.model.LocalMovie) {
        localDataSource.removeSelectedMovie(movie)
    }

    suspend fun removeSelectedTV(tv: com.andika.architecturecomponent.core.business.data.local.model.LocalTV) {
        localDataSource.removeSelectedTV(tv)
    }

    fun getSelectedMovie(id: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            val data = localDataSource.getSelectedMovie(id).toMovie()
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }


    fun getSelectedTV(id: Int): Flow<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>> = flow {
        emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        try {
            val data = localDataSource.getSelectedTV(id).toTV()
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(data))
        } catch (e: Exception) {
            emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(e))
        }
    }
}