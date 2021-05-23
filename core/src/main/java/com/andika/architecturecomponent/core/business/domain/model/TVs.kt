package com.andika.architecturecomponent.core.business.domain.model


data class TVs(
    val dates: Dates?,
    val page: Int,
    var results: List<TV>,
    val total_pages: Int,
    val total_results: Int
)