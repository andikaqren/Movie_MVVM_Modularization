package com.andika.architecturecomponent.core.business.domain.utils

import com.andika.architecturecomponent.core.BuildConfig


object AppConstant {
    const val baseUrl = BuildConfig.SERVER_URL
    const val movieDBKEY = BuildConfig.MOVIE_DB_KEY
    const val baseDomain = BuildConfig.SERVER_DOMAIN
    const val POSTER_URL_500 = BuildConfig.SERVER_IMAGE_URL
    const val posterDomain = BuildConfig.SERVER_IMAGE_DOMAIN
    const val DEEPLINKURL = BuildConfig.DEEP_LINK_URL
    const val deepLinkDomain = BuildConfig.DEEP_LINK_DOMAIN
    const val TABLE_GENRE = "Genre"
    const val MOVIE = "MOVIE"
    const val TYPE = "type"
    const val TV = "TV"
    const val ID = "id"
    const val EMPTY = ""
    const val UPCOMING_MOVIES = "Upcoming_Movies"
    const val NOW_PLAYING_MOVIES = "Now_Playing_Movies"
    const val POPULAR_MOVIES = "Popular_Movies"
    const val POPULAR_TV = "Popular_TV"
    const val LATEST_TV = "Latest_TV"
    const val PAGE_SIZE = 50
    const val UNKNOWN = "UNKNOWN"


}
