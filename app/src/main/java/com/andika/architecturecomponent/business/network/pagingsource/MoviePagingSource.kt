package com.andika.architecturecomponent.business.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andika.architecturecomponent.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.business.data.remote.model.Movie
import com.andika.architecturecomponent.business.data.remote.model.Movies
import com.andika.architecturecomponent.business.domain.utils.AppConstant.NOW_PLAYING_MOVIES
import com.andika.architecturecomponent.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.andika.architecturecomponent.business.domain.utils.AppConstant.UPCOMING_MOVIES
import com.bumptech.glide.load.HttpException
import java.io.IOException


class MoviePagingSource(
    private val service: RemoteDataSource,
    private val query: String
) : PagingSource<Int, Movie>() {
    private val MOVIE_STARTING_PAGE_INDEX = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
            val response: Movies = when (query) {
                UPCOMING_MOVIES -> service.getUpcoming(position)
                NOW_PLAYING_MOVIES -> service.getNowPlaying(position)
                POPULAR_MOVIES -> service.getPopular(position)
                else -> service.getUpcoming(position)
            }
            val nextKey = if (response.results.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}