package com.andika.architecturecomponent.business.network

import com.andika.architecturecomponent.business.data.remote.model.*

class NetworkManagerServiceImpl
constructor(
    private val networkmanager: NetworkManager
) : NetworkManagerService {
    override suspend fun getTopMovies(page: Int): Movies {
        return networkmanager.getTopMovies(page)
    }

    override suspend fun getVideo(filename: Int?): Videos {
        return networkmanager.getVideo(filename)
    }

    override suspend fun getSimilar(filename: Int?): Movies {
        return networkmanager.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): TVs {
        return networkmanager.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): Movies {
        return networkmanager.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): TVs {
        return networkmanager.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): Movies {
        return networkmanager.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): Movies {
        return networkmanager.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): Movies {
        return networkmanager.getPopular(page)
    }

    override suspend fun getKategori(): Category {
        return networkmanager.getKategori()
    }

    override suspend fun getTopRatedTV(): TVs {
        return networkmanager.getTopRatedTv()
    }

    override suspend fun getPopularTV(page: Int): TVs {
        return networkmanager.getPopularTv(page)
    }

    override suspend fun getLatestTV(page: Int): TVs {
        return networkmanager.getLatestTv(page)
    }

    override suspend fun getDetailTV(id: String): TV {
        return networkmanager.getDetailTV(id.toInt())
    }

    override suspend fun getDetailMovie(id: String): Movie {
        return networkmanager.getDetailMovie(id.toInt())
    }

}