package com.andika.architecturecomponent.business.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.data.local.model.LocalGenre
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.domain.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl
constructor(
    private val daoService: LocalDao
) : LocalDataSource {
    override suspend fun insertSelectedMovie(movie: LocalMovie): Long {
        return daoService.insertSelectedMovie(movie)
    }

    override suspend fun insertSelectedTV(movie: LocalTV): Long {
        return daoService.insertSelectedTV(movie)
    }

    override suspend fun removeSelectedMovie(movie: LocalMovie) {
        return daoService.deleteMovie(movie)
    }

    override suspend fun removeSelectedTV(movie: LocalTV) {
        return daoService.deleteTV(movie)
    }

    override suspend fun insertGenres(genres: List<LocalGenre>) {
        return daoService.insertSelectedGenres(genres)
    }

    override fun getSelectedMovie(id: Int): LocalMovie {
        return daoService.getSelectedMovie(id)
    }

    override fun getSelectedTV(id: Int): LocalTV {
        return daoService.getSelectedTV(id)
    }

    override fun getAllFavMovies(): Flow<PagingData<LocalMovie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { daoService.getAllFavouriteMovie() }
        ).flow
    }

    override fun getAllFavTV(): Flow<PagingData<LocalTV>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                daoService.getAllFavouriteTV()
            }
        ).flow
    }

    override fun getGenres(): List<LocalGenre> {
        return daoService.getGenres()
    }


}