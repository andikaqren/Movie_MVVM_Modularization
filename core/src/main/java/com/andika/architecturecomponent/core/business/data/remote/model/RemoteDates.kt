package com.andika.architecturecomponent.core.business.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteDates(
    @field:SerializedName("maximum")
    val maximum: String,
    @field:SerializedName("minimum")
    val minimum: String
)