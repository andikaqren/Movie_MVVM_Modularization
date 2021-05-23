package com.andika.architecturecomponent.core.business.domain.model


data class Movies(
    val dates: Dates?,
    val page: Int,
    var results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)