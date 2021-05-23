package com.andika.architecturecomponent.core.business.data.remote.model

data class RemoteMovies(
    val dates: com.andika.architecturecomponent.core.business.data.remote.model.RemoteDates?,
    val page: Int,
    var results: List<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>,
    val total_pages: Int,
    val total_results: Int
)