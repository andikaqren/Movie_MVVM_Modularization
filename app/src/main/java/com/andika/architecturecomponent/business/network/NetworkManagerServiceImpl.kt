package com.andika.architecturecomponent.business.network

import com.andika.architecturecomponent.business.data.remote.model.*

class NetworkManagerServiceImpl
constructor(
    private val networkmanager: NetworkManager
) : NetworkManagerService {
    override suspend fun getTopMovies(page: Int): RemoteMovies {
        return networkmanager.getTopMovies(page)
    }

    override suspend fun getVideo(filename: Int?): RemoteVideos {
        return networkmanager.getVideo(filename)
    }

    override suspend fun getSimilar(filename: Int?): RemoteMovies {
        return networkmanager.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): RemoteTVs {
        return networkmanager.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): RemoteMovies {
        return networkmanager.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): RemoteTVs {
        return networkmanager.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): RemoteMovies {
        return networkmanager.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): RemoteMovies {
        return networkmanager.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): RemoteMovies {
        return networkmanager.getPopular(page)
    }

    override suspend fun getKategori(): RemoteCategory {
        return networkmanager.getKategori()
    }

    override suspend fun getTopRatedTV(): RemoteTVs {
        return networkmanager.getTopRatedTv()
    }

    override suspend fun getPopularTV(page: Int): RemoteTVs {
        return networkmanager.getPopularTv(page)
    }

    override suspend fun getLatestTV(page: Int): RemoteTVs {
        return networkmanager.getLatestTv(page)
    }

    override suspend fun getDetailTV(id: String): RemoteTV {
        return networkmanager.getDetailTV(id.toInt())
    }

    override suspend fun getDetailMovie(id: String): RemoteMovie {
        return networkmanager.getDetailMovie(id.toInt())
    }

}