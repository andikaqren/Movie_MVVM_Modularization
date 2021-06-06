package com.andika.architecturecomponent.core.business.data.remote.model

import com.google.gson.annotations.SerializedName


data class RemoteGenre(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)