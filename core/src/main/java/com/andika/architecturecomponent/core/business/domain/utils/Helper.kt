package com.andika.architecturecomponent.core.business.domain.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.domain.utils.toLocalMovieList
import com.andika.architecturecomponent.business.domain.utils.toLocalTVList
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.data.remote.model.*
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.PAGE_SIZE
import com.andika.architecturecomponent.core.business.network.pagingsource.MoviePagingSource
import com.andika.architecturecomponent.core.business.network.pagingsource.TVPagingSource

object Helper {

    fun getDummyMovies() = RemoteMovies(
        RemoteDates("100", "100"),
        page = 10,
        results = getDummyMovie(),
        total_results = 10,
        total_pages = 5
    )

    fun getDummyTVs() = RemoteTVs(
        RemoteDates("100", "100"),
        page = 10,
        results = getDummyTV(),
        total_results = 10,
        total_pages = 5
    )


    fun getDummyLocalTV() = PagingData.from(getDummyTV().toLocalTVList())

    fun getDummyLocalMovie() = PagingData.from(getDummyMovie().toLocalMovieList())


    fun getDummyTVPager(): PagingData<RemoteTV> {
        return PagingData.from(getDummyTV())
    }

    fun getDummyMoviePager(): PagingData<RemoteMovie> {
        return PagingData.from(getDummyMovie())
    }

    private fun getDummyMovie(): MutableList<RemoteMovie> {
        val dummy = mutableListOf<RemoteMovie>()
        repeat(10) {
            dummy.add(
                RemoteMovie(
                    adult = false,
                    genre_ids = null,
                    id = 100,
                    vote_count = 100,
                    vote_average = 10.0,
                    video = true,
                    title = "DUMMY",
                    release_date = "2012-20-10",
                    poster_path = "1028109",
                    popularity = 10.0,
                    overview = "DUMMY NICE NICE",
                    original_title = "DUMMY GOOD",
                    original_language = "SPANISH",
                    backdrop_path = "21892189"
                )
            )
        }
        return dummy
    }

    private fun getDummyTV(): MutableList<RemoteTV> {
        val dummy = mutableListOf<RemoteTV>()
        repeat(10) {
            dummy.add(
                RemoteTV(
                    genre_ids = null,
                    id = 100,
                    vote_count = 100,
                    vote_average = 10.0,
                    video = true,
                    name = "DUMMY",
                    backdrop_path = "2189218912",
                    original_language = "NIGERIA",
                    popularity = 10.0,
                    poster_path = "1028109",
                    overview = "DUMMY NICE NICE",
                    origin_country = null,
                    first_air_date = "2012",
                    original_name = "DUMMY FIGHTER"
                )
            )
        }
        return dummy
    }


    fun getMoviePager(service: RemoteDataSource, query: String) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(service = service, query = query) }
        ).flow

    fun getTVPager(service: RemoteDataSource, query: String) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { TVPagingSource(service = service, query = query) }
        ).flow


}