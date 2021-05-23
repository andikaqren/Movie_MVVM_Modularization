package com.andika.architecturecomponent.business.network

import com.andika.architecturecomponent.business.data.remote.model.*

interface NetworkManagerService {
    suspend fun getTopMovies(page: Int): RemoteMovies
    suspend fun getVideo(filename: Int?): RemoteVideos
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
    suspend fun getDetailMovie(id: String): RemoteMovie
}