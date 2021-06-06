package com.andika.architecturecomponent.core.business.data.remote.model

import com.google.gson.annotations.SerializedName


data class RemoteCategory(
    @field:SerializedName("genres")
    var genres: List<RemoteGenre>
)