package com.andika.architecturecomponent.business.data.remote

import com.andika.architecturecomponent.business.data.remote.model.*
import com.andika.architecturecomponent.business.network.NetworkManagerService

class RemoteDataSourceImpl
constructor(
    private val networkManagerService: NetworkManagerService
) : RemoteDataSource {
    override suspend fun getTopMovies(page: Int): Movies {
        return networkManagerService.getTopMovies(page)
    }

    override suspend fun getDetailMovie(id: String): Movie {
        return networkManagerService.getDetailMovie(id)
    }

    override suspend fun getVideo(filename: Int?): Videos {
        return networkManagerService.getVideo(filename)
    }

    override suspend fun getSimilar(filename: Int?): Movies {
        return networkManagerService.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): TVs {
        return networkManagerService.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): Movies {
        return networkManagerService.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): TVs {
        return networkManagerService.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): Movies {
        return networkManagerService.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): Movies {
        return networkManagerService.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): Movies {
        return networkManagerService.getPopular(page)
    }

    override suspend fun getKategori(): Category {
        return networkManagerService.getKategori()
    }

    override suspend fun getTopRatedTV(): TVs {
        return networkManagerService.getTopRatedTV()
    }

    override suspend fun getPopularTV(page: Int): TVs {
        return networkManagerService.getPopularTV(page)
    }

    override suspend fun getLatestTV(page: Int): TVs {
        return networkManagerService.getLatestTV(page)
    }

    override suspend fun getDetailTV(id: String): TV {
        return networkManagerService.getDetailTV(id)
    }

}