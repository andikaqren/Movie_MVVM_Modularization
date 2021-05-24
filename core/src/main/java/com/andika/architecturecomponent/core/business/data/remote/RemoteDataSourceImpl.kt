package com.andika.architecturecomponent.core.business.data.remote

import com.andika.architecturecomponent.core.business.data.remote.model.*
import com.andika.architecturecomponent.core.business.network.NetworkManagerService

class RemoteDataSourceImpl
constructor(
    private val networkManagerService: NetworkManagerService
) : RemoteDataSource {
    override suspend fun getTopMovies(page: Int): RemoteMovies {
        return networkManagerService.getTopMovies(page)
    }

    override suspend fun getDetailMovie(id: String): RemoteMovie {
        return networkManagerService.getDetailMovie(id)
    }

    override suspend fun getSimilar(filename: Int?): RemoteMovies {
        return networkManagerService.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): RemoteTVs {
        return networkManagerService.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): RemoteMovies {
        return networkManagerService.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): RemoteTVs {
        return networkManagerService.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): RemoteMovies {
        return networkManagerService.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): RemoteMovies {
        return networkManagerService.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): RemoteMovies {
        return networkManagerService.getPopular(page)
    }

    override suspend fun getKategori(): RemoteCategory {
        return networkManagerService.getKategori()
    }

    override suspend fun getTopRatedTV(): RemoteTVs {
        return networkManagerService.getTopRatedTV()
    }

    override suspend fun getPopularTV(page: Int): RemoteTVs {
        return networkManagerService.getPopularTV(page)
    }

    override suspend fun getLatestTV(page: Int): RemoteTVs {
        return networkManagerService.getLatestTV(page)
    }

    override suspend fun getDetailTV(id: String): RemoteTV {
        return networkManagerService.getDetailTV(id)
    }

}