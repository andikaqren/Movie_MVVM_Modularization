package com.andika.architecturecomponent.core.business.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteVideos(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("results")
    val results: List<RemoteVideo>
)