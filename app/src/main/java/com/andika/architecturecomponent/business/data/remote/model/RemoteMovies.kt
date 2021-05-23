package com.andika.architecturecomponent.business.data.remote.model

data class RemoteMovies(
    val dates: RemoteDates?,
    val page: Int,
    var results: List<RemoteMovie>,
    val total_pages: Int,
    val total_results: Int
)