package com.andika.architecturecomponent.business.data.remote.model

data class TVs(
    val dates: Dates?,
    val page: Int,
    var results: List<TV>,
    val total_pages: Int,
    val total_results: Int
)