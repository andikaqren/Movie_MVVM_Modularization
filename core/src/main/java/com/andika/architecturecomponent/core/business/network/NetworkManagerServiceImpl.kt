package com.andika.architecturecomponent.core.business.network

import com.andika.architecturecomponent.core.business.data.remote.model.*

class NetworkManagerServiceImpl
constructor(
    private val networkManager: NetworkManager
) : NetworkManagerService {
    override suspend fun getTopMovies(page: Int): RemoteMovies {
        return networkManager.getTopMovies(page)
    }

    override suspend fun getVideo(filename: Int?): RemoteVideos {
        return networkManager.getVideo(filename)
    }

    override suspend fun getSimilar(filename: Int?): RemoteMovies {
        return networkManager.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): RemoteTVs {
        return networkManager.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): RemoteMovies {
        return networkManager.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): RemoteTVs {
        return networkManager.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): RemoteMovies {
        return networkManager.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): RemoteMovies {
        return networkManager.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): RemoteMovies {
        return networkManager.getPopular(page)
    }

    override suspend fun getKategori(): RemoteCategory {
        return networkManager.getKategori()
    }

    override suspend fun getTopRatedTV(): RemoteTVs {
        return networkManager.getTopRatedTv()
    }

    override suspend fun getPopularTV(page: Int): RemoteTVs {
        return networkManager.getPopularTv(page)
    }

    override suspend fun getLatestTV(page: Int): RemoteTVs {
        return networkManager.getLatestTv(page)
    }

    override suspend fun getDetailTV(id: String): RemoteTV {
        return networkManager.getDetailTV(id.toInt())
    }

    override suspend fun getDetailMovie(id: String): RemoteMovie {
        return networkManager.getDetailMovie(id.toInt())
    }

}