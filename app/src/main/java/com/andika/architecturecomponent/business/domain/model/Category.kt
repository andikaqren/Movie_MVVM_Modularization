package com.andika.architecturecomponent.business.domain.model

import com.andika.architecturecomponent.business.data.remote.model.RemoteGenre

data class Category(
    var genres: List<Genre>
)