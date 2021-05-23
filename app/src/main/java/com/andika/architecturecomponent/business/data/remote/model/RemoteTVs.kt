package com.andika.architecturecomponent.business.data.remote.model

data class RemoteTVs(
    val dates: RemoteDates?,
    val page: Int,
    var results: List<RemoteTV>,
    val total_pages: Int,
    val total_results: Int
)