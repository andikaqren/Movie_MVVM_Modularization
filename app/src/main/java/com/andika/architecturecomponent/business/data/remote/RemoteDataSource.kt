package com.andika.architecturecomponent.business.data.remote

import com.andika.architecturecomponent.business.data.remote.model.*

interface RemoteDataSource {
    suspend fun getTopMovies(page: Int): Movies
    suspend fun getDetailMovie(id: String): Movie
    suspend fun getVideo(filename: Int?): Videos
    suspend fun getSimilar(filename: Int?): Movies
    suspend fun getSimilarTV(filename: Int?): TVs
    suspend fun getRecomendation(filename: Int?): Movies
    suspend fun getRecomendationTV(filename: Int?): TVs
    suspend fun getNowPlaying(page: Int): Movies
    suspend fun getUpcoming(page: Int): Movies
    suspend fun getPopular(page: Int): Movies
    suspend fun getKategori(): Category
    suspend fun getTopRatedTV(): TVs
    suspend fun getPopularTV(page: Int): TVs
    suspend fun getLatestTV(page: Int): TVs
    suspend fun getDetailTV(id: String): TV
}