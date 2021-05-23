package com.andika.architecturecomponent.core.business.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TV(
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val origin_country: List<String>?,
    val id: Int,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val first_air_date: String?,
    val name: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) : Parcelable