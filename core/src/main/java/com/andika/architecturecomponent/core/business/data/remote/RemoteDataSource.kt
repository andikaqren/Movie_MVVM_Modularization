package com.andika.architecturecomponent.core.business.data.remote

import com.andika.architecturecomponent.core.business.data.remote.model.*

interface RemoteDataSource {
    suspend fun getTopMovies(page: Int): RemoteMovies
    suspend fun getDetailMovie(id: String): RemoteMovie
    suspend fun getSimilar(filename: Int?): RemoteMovies
    suspend fun getSimilarTV(filename: Int?): RemoteTVs
    suspend fun getRecomendation(filename: Int?): RemoteMovies
    suspend fun getRecomendationTV(filename: Int?): RemoteTVs
    suspend fun getNowPlaying(page: Int): RemoteMovies
    suspend fun getUpcoming(page: Int): RemoteMovies
    suspend fun getPopular(page: Int): RemoteMovies
    suspend fun getKategori(): RemoteCategory
    suspend fun getTopRatedTV(): RemoteTVs
    suspend fun getPopularTV(page: Int): RemoteTVs
    suspend fun getLatestTV(page: Int): RemoteTVs
    suspend fun getDetailTV(id: String): RemoteTV
}