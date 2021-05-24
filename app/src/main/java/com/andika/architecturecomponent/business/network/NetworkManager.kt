package com.andika.architecturecomponent.business.network

import com.andika.architecturecomponent.business.data.remote.model.*
import com.andika.architecturecomponent.business.domain.utils.AppConstant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkManager {
    @GET("movie/top_rated?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getTopMovies(@Query("page") page: Int): Movies

    @GET("movie/{fileName}/videos?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getVideo(@Path("fileName") filename: Int?): Videos

    @GET("movie/{fileName}/similar?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getSimilar(@Path("fileName") filename: Int?): Movies

    @GET("movie/{fileName}/recommendations?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getRecomendation(@Path("fileName") filename: Int?): Movies

    @GET("tv/{fileName}/similar?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getSimilarTV(@Path("fileName") filename: Int?): TVs

    @GET("tv/{fileName}/recommendations?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getRecomendationTV(@Path("fileName") filename: Int?): TVs

    @GET("movie/now_playing?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getNowPlaying(@Query("page") page: Int): Movies

    @GET("movie/upcoming?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getUpcoming(@Query("page") page: Int): Movies

    @GET("movie/popular?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getPopular(@Query("page") page: Int): Movies

    @GET("tv/popular?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getPopularTv(@Query("page") page: Int): TVs

    @GET("tv/on_the_air?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getLatestTv(@Query("page") page: Int): TVs

    @GET("tv/top_rated?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getTopRatedTv(): TVs

    @GET("genre/movie/list?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getKategori(): Category

    @GET("tv/{tv_id}?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getDetailTV(@Path("tv_id") fileId: Int): TV

    @GET("movie/{movie_id}?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getDetailMovie(@Path("movie_id") fileId: Int): Movie
}