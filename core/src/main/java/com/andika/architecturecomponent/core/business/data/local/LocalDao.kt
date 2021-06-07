package com.andika.architecturecomponent.core.business.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.andika.architecturecomponent.core.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.core.business.data.local.model.LocalTV


@Dao
interface LocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedMovie(movie: LocalMovie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedTV(movie: LocalTV): Long

    @Delete
    fun deleteMovie(movie: LocalMovie)

    @Delete
    fun deleteTV(movie: LocalTV)

    @Query("SELECT * FROM MOVIE WHERE id = :id")
    fun getSelectedMovie(id: Int): LocalMovie

    @Query("SELECT * FROM TV WHERE id = :id")
    fun getSelectedTV(id: Int): LocalTV

    @Query("SELECT * FROM MOVIE")
    fun getAllFavouriteMovie(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM TV")
    fun getAllFavouriteTV(): PagingSource<Int, LocalTV>

}