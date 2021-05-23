package com.andika.architecturecomponent.core.business.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TV")
class LocalTV(
    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "original_language")
    val original_language: String?,
    @ColumnInfo(name = "original_name")
    val original_name: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double?,
    @ColumnInfo(name = "poster_path")
    val poster_path: String?,
    @ColumnInfo(name = "first_air_date")
    val first_air_date: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "video")
    val video: Boolean?,
    @ColumnInfo(name = "vote_average")
    val vote_average: Double?,
    @ColumnInfo(name = "vote_count")
    val vote_count: Int?

)