package com.andika.architecturecomponent.business.domain.utils

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

import com.andika.architecturecomponent.business.data.local.model.LocalGenre
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.data.remote.model.Genre
import com.andika.architecturecomponent.business.data.remote.model.Movie
import com.andika.architecturecomponent.business.data.remote.model.TV
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


fun LocalMovie.toMovie(): Movie = Movie(
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

fun List<LocalMovie>.toMovieList(): List<Movie> {
    val localMovies = mutableListOf<Movie>()
    for (movie in this) {
        localMovies.add(
            Movie(
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

fun List<Movie>.toLocalMovieList(): List<LocalMovie> {
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

fun List<TV>.toLocalTVList(): List<LocalTV> {
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

fun List<LocalTV>.toTVList(): List<TV> {
    val tvList = mutableListOf<TV>()
    for (tv in this) {
        tvList.add(
            TV(
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

fun Movie.toLocalMovie(): LocalMovie =
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

fun TV.toLocalTV(): LocalTV =
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

fun LocalTV.toTV(): TV =
    TV(
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

fun List<Genre>.toLocalGenres(): List<LocalGenre> {
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

fun List<LocalGenre>.toGenres(): List<Genre> {
    val localGenre = mutableListOf<Genre>()
    for (genre in this) {
        localGenre.add(Genre(id = genre.id, name = genre.name))
    }
    return localGenre
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}


