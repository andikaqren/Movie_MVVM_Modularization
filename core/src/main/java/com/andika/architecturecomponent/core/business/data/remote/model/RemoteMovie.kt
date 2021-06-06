package com.andika.architecturecomponent.core.business.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RemoteMovie(
    @field:SerializedName("adult")
    val adult: Boolean?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("genre_ids")
    val genre_ids: List<Int>?,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("original_language")
    val original_language: String?,
    @field:SerializedName("original_title")
    val original_title: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("release_date")
    val release_date: String?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("video")
    val video: Boolean?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("vote_count")
    val vote_count: Int?

) : Parcelable