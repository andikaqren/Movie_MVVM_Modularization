package com.andika.architecturecomponent.core.business.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RemoteTV(
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("genre_ids")
    val genre_ids: List<Int>?,
    @field:SerializedName("origin_country")
    val origin_country: List<String>?,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("original_language")
    val original_language: String?,
    @field:SerializedName("original_name")
    val original_name: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("first_air_date")
    val first_air_date: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("video")
    val video: Boolean?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("vote_count")
    val vote_count: Int?
) : Parcelable