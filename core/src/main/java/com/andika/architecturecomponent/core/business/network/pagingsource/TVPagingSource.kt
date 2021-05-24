package com.andika.architecturecomponent.core.business.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.LATEST_TV
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.POPULAR_TV
import retrofit2.HttpException
import java.io.IOException


class TVPagingSource(
    private val service: RemoteDataSource,
    private val query: String
) : PagingSource<Int, TV>() {
    private val TV_STARTING_PAGE_INDEX = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TV> {
        val position = params.key ?: TV_STARTING_PAGE_INDEX
        return try {
            val response: RemoteTVs = when (query) {
                POPULAR_TV -> service.getPopularTV(position)
                LATEST_TV -> service.getLatestTV(position)
                else -> service.getPopularTV(position)
            }
            val nextKey = if (response.results.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = com.andika.architecturecomponent.core.business.domain.utils.DataMapper.listRemoteTVToTV(
                    response.results
                ),
                prevKey = if (position == TV_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TV>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}