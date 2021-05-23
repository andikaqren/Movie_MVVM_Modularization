package com.andika.architecturecomponent.business.domain.model

import com.andika.architecturecomponent.business.data.remote.model.RemoteDates
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV

data class TVs(
    val dates: RemoteDates?,
    val page: Int,
    var results: List<RemoteTV>,
    val total_pages: Int,
    val total_results: Int
)