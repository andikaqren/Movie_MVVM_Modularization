package com.andika.architecturecomponent.core.business.domain.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.andika.architecturecomponent.core.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.PAGE_SIZE
import com.andika.architecturecomponent.core.business.network.pagingsource.MoviePagingSource
import com.andika.architecturecomponent.core.business.network.pagingsource.TVPagingSource

object Helper {

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