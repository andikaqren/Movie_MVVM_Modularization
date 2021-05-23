package com.andika.architecturecomponent.core.business.network

import com.andika.architecturecomponent.business.data.remote.model.*

interface NetworkManagerService {
    suspend fun getTopMovies(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
    suspend fun getVideo(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteVideos
    suspend fun getSimilar(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
    suspend fun getSimilarTV(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
    suspend fun getRecomendation(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
    suspend fun getRecomendationTV(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
    suspend fun getNowPlaying(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
    suspend fun getUpcoming(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
    suspend fun getPopular(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
    suspend fun getKategori(): com.andika.architecturecomponent.core.business.data.remote.model.RemoteCategory
    suspend fun getTopRatedTV(): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
    suspend fun getPopularTV(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
    suspend fun getLatestTV(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
    suspend fun getDetailTV(id: String): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
    suspend fun getDetailMovie(id: String): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
}