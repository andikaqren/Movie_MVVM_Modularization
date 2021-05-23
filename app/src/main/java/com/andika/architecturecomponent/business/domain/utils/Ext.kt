package com.andika.architecturecomponent.business.domain.utils

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

import com.andika.architecturecomponent.business.data.local.model.LocalGenre
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteGenre
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


fun LocalMovie.toMovie(): RemoteMovie = RemoteMovie(
    id = id,
    adult = adult,
    backdrop_path = backdrop_path,
    original_language = original_language,
    genre_ids = null,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    poster_path = poster_path,
    release_date = release_date,
    title = title,
    video = video,
    vote_average = vote_average,
    vote_count = vote_count
)

fun List<LocalMovie>.toMovieList(): List<RemoteMovie> {
    val localMovies = mutableListOf<RemoteMovie>()
    for (movie in this) {
        localMovies.add(
            RemoteMovie(
                id = movie.id,
                adult = movie.adult,
                backdrop_path = movie.backdrop_path,
                original_language = movie.original_language,
                genre_ids = null,
                original_title = movie.original_title,
                overview = movie.overview,
                popularity = movie.popularity,
                poster_path = movie.poster_path,
                release_date = movie.release_date,
                title = movie.title,
                video = movie.video,
                vote_average = movie.vote_average,
                vote_count = movie.vote_count
            )
        )
    }
    return localMovies

}

fun List<RemoteMovie>.toLocalMovieList(): List<LocalMovie> {
    val localMovies = mutableListOf<LocalMovie>()
    for (movie in this) {
        localMovies.add(
            LocalMovie(
                id = movie.id,
                adult = movie.adult,
                backdrop_path = movie.backdrop_path,
                original_language = movie.original_language,
                original_title = movie.original_title,
                overview = movie.overview,
                popularity = movie.popularity,
                poster_path = movie.poster_path,
                release_date = movie.release_date,
                title = movie.title,
                video = movie.video,
                vote_average = movie.vote_average,
                vote_count = movie.vote_count
            )
        )
    }
    return localMovies

}

fun List<RemoteTV>.toLocalTVList(): List<LocalTV> {
    val tvList = mutableListOf<LocalTV>()
    for (tv in this) {
        tvList.add(
            LocalTV(
                id = tv.id,
                backdrop_path = tv.backdrop_path,
                original_language = tv.original_language,
                original_name = tv.original_name,
                overview = tv.overview,
                popularity = tv.popularity,
                poster_path = tv.poster_path,
                first_air_date = tv.first_air_date,
                name = tv.name,
                video = tv.video,
                vote_average = tv.vote_average,
                vote_count = tv.vote_count
            )
        )
    }
    return tvList
}

fun List<LocalTV>.toTVList(): List<RemoteTV> {
    val tvList = mutableListOf<RemoteTV>()
    for (tv in this) {
        tvList.add(
            RemoteTV(
                id = tv.id,
                backdrop_path = tv.backdrop_path,
                original_language = tv.original_language,
                genre_ids = null,
                original_name = tv.original_name,
                overview = tv.overview,
                popularity = tv.popularity,
                poster_path = tv.poster_path,
                first_air_date = tv.first_air_date,
                name = tv.name,
                video = tv.video,
                vote_average = tv.vote_average,
                vote_count = tv.vote_count, origin_country = null
            )
        )
    }
    return tvList
}

fun RemoteMovie.toLocalMovie(): LocalMovie =
    LocalMovie(
        id = id,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )

fun RemoteTV.toLocalTV(): LocalTV =
    LocalTV(
        id = id,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_name = original_name,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        first_air_date = first_air_date,
        name = name,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )

fun LocalTV.toTV(): RemoteTV =
    RemoteTV(
        id = id,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_name = original_name,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        first_air_date = first_air_date,
        name = name,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count, genre_ids = null, origin_country = null
    )

fun List<RemoteGenre>.toLocalGenres(): List<LocalGenre> {
    val localGenre = mutableListOf<LocalGenre>()
    for (genre in this) {
        localGenre.add(
            LocalGenre(
                id = genre.id,
                name = genre.name
            )
        )
    }
    return localGenre
}

fun List<LocalGenre>.toGenres(): List<RemoteGenre> {
    val localGenre = mutableListOf<RemoteGenre>()
    for (genre in this) {
        localGenre.add(RemoteGenre(id = genre.id, name = genre.name))
    }
    return localGenre
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}



