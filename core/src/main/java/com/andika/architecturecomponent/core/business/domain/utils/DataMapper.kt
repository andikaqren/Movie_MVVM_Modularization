package com.andika.architecturecomponent.core.business.domain.utils

import com.andika.architecturecomponent.core.business.data.remote.model.*
import com.andika.architecturecomponent.core.business.data.local.model.*
import com.andika.architecturecomponent.core.business.domain.model.*
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.UNKNOWN

object DataMapper {
    fun listRemoteMovieToMovie(input: List<RemoteMovie>): List<Movie> {
        val movieList = ArrayList<Movie>()
        input.map { data ->
            val movie = Movie(
                id = data.id,
                adult = data.adult,
                backdrop_path = data.backdrop_path,
                original_language = data.original_language,
                genre_ids = data.genre_ids,
                original_title = data.original_title,
                overview = data.overview,
                popularity = data.popularity,
                poster_path = data.poster_path,
                release_date = data.release_date,
                title = data.title,
                video = data.video,
                vote_average = data.vote_average,
                vote_count = data.vote_count
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun listRemoteTVToTV(input: List<RemoteTV>): List<TV> {
        val tvList = ArrayList<TV>()
        input.map { data ->
            val tv = TV(
                id = data.id,
                backdrop_path = data.backdrop_path,
                original_language = data.original_language,
                genre_ids = data.genre_ids,
                original_name = data.original_name,
                overview = data.overview,
                popularity = data.popularity,
                poster_path = data.poster_path,
                first_air_date = data.first_air_date,
                name = data.name,
                video = data.video,
                vote_average = data.vote_average,
                vote_count = data.vote_count, origin_country = data.origin_country
            )
            tvList.add(tv)
        }
        return tvList
    }

    fun remoteMoviesToMovies(input: RemoteMovies): Movies {
        return Movies(
            dates = Dates(
                maximum = input.dates?.maximum ?: UNKNOWN,
                minimum = input.dates?.minimum ?: UNKNOWN
            ),
            page = input.page,
            results = listRemoteMovieToMovie(input.results),
            total_pages = input.total_pages,
            total_results = input.total_results
        )
    }

    fun remoteTVsToTVs(input: RemoteTVs): TVs {
        return TVs(
            dates = Dates(
                maximum = input.dates?.maximum ?: UNKNOWN,
                minimum = input.dates?.minimum ?: UNKNOWN
            ),
            page = input.page,
            results = listRemoteTVToTV(input.results),
            total_pages = input.total_pages,
            total_results = input.total_results
        )
    }

    fun tvToLocalTV(input: TV): LocalTV {
        return LocalTV(
            id = input.id,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            original_name = input.original_name,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            first_air_date = input.first_air_date,
            name = input.name,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )
    }

    fun localTVToTV(input: LocalTV): TV {
        return TV(
            id = input.id,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            original_name = input.original_name,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            first_air_date = input.first_air_date,
            name = input.name,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count, genre_ids = null, origin_country = null
        )
    }

    fun remoteTVToTV(input: RemoteTV): TV {
        return TV(
            id = input.id,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            original_name = input.original_name,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            first_air_date = input.first_air_date,
            name = input.name,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count,
            genre_ids = input.genre_ids,
            origin_country = input.origin_country
        )
    }

    fun remoteMovieToMovie(input: RemoteMovie): Movie {
        return Movie(
            id = input.id,
            adult = input.adult,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            genre_ids = input.genre_ids,
            original_title = input.original_title,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            release_date = input.release_date,
            title = input.title,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )
    }

    fun localMovieToMovie(input: LocalMovie): Movie {
        return Movie(
            id = input.id,
            adult = input.adult,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            genre_ids = null,
            original_title = input.original_title,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            release_date = input.release_date,
            title = input.title,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )
    }

    fun movieToLocalMovie(input: Movie): LocalMovie {
        return LocalMovie(
            id = input.id,
            adult = input.adult,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            original_title = input.original_title,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            release_date = input.release_date,
            title = input.title,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )
    }


}