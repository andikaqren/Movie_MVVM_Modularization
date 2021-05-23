package com.andika.architecturecomponent.core.business.data.local

import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.data.local.model.LocalGenre
import com.andika.architecturecomponent.core.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.core.business.data.local.model.LocalTV
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertSelectedMovie(movie: LocalMovie): Long
    suspend fun insertSelectedTV(movie: LocalTV): Long
    suspend fun removeSelectedMovie(movie: LocalMovie)
    suspend fun removeSelectedTV(movie: LocalTV)
    suspend fun insertGenres(genres: List<LocalGenre>)
    fun getSelectedMovie(id: Int): LocalMovie
    fun getSelectedTV(id: Int): LocalTV
    fun getAllFavMovies(): Flow<PagingData<LocalMovie>>
    fun getAllFavTV(): Flow<PagingData<LocalTV>>
    fun getGenres(): List<LocalGenre>
}