package com.andika.architecturecomponent.core.business.network

class NetworkManagerServiceImpl
constructor(
    private val networkmanager: com.andika.architecturecomponent.core.business.network.NetworkManager
) : com.andika.architecturecomponent.core.business.network.NetworkManagerService {
    override suspend fun getTopMovies(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies {
        return networkmanager.getTopMovies(page)
    }

    override suspend fun getVideo(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteVideos {
        return networkmanager.getVideo(filename)
    }

    override suspend fun getSimilar(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies {
        return networkmanager.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs {
        return networkmanager.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies {
        return networkmanager.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs {
        return networkmanager.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies {
        return networkmanager.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies {
        return networkmanager.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies {
        return networkmanager.getPopular(page)
    }

    override suspend fun getKategori(): com.andika.architecturecomponent.core.business.data.remote.model.RemoteCategory {
        return networkmanager.getKategori()
    }

    override suspend fun getTopRatedTV(): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs {
        return networkmanager.getTopRatedTv()
    }

    override suspend fun getPopularTV(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs {
        return networkmanager.getPopularTv(page)
    }

    override suspend fun getLatestTV(page: Int): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs {
        return networkmanager.getLatestTv(page)
    }

    override suspend fun getDetailTV(id: String): com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV {
        return networkmanager.getDetailTV(id.toInt())
    }

    override suspend fun getDetailMovie(id: String): com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie {
        return networkmanager.getDetailMovie(id.toInt())
    }

}