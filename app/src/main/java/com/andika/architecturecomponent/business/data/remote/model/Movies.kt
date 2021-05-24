package com.andika.architecturecomponent.business.data.remote.model

data class Movies(
    val dates: Dates?,
    val page: Int,
    var results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)