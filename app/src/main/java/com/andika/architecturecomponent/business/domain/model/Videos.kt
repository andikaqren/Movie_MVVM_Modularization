package com.andika.architecturecomponent.business.domain.model

import com.andika.architecturecomponent.business.data.remote.model.RemoteVideo

data class Videos(
    val id: Int,
    val results: List<Video>
)