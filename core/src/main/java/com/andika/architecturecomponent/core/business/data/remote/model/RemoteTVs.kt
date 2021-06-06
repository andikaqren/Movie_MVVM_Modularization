package com.andika.architecturecomponent.core.business.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteTVs(
    @field:SerializedName("dates")
    val dates: RemoteDates?,
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    var results: List<RemoteTV>,
    @field:SerializedName("total_pages")
    val total_pages: Int,
    @field:SerializedName("total_results")
    val total_results: Int
)