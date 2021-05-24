package com.andika.architecturecomponent.framework.presentation.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val interactors: MovieInteractors
) : ViewModel() {
    private var nowPlayingJob: Job? = null
    private var popularJob: Job? = null
    private var upcomingJob: Job? = null
    private var topRatedJob: Job? = null
    private var _topMovies: MutableLiveData<DataState<Movies>> = MutableLiveData()
    private var _nowPlayingMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    private var _popularMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var _upcomingMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var topMovies: LiveData<DataState<Movies>> = _topMovies
    var popularMovies: LiveData<DataState<PagingData<Movie>>> = _popularMovies
    var nowPlayingMovies: LiveData<DataState<PagingData<Movie>>> = _nowPlayingMovies
    var upcomingMovies: LiveData<DataState<PagingData<Movie>>> = _upcomingMovies

    fun reloadData() {
        nowPlayingJob?.cancel()
        popularJob?.cancel()
        upcomingJob?.cancel()
        topRatedJob?.cancel()
        nowPlayingJob = viewModelScope.launch(Dispatchers.IO) {
            getNowPlayingMovies()
        }
        upcomingJob = viewModelScope.launch(Dispatchers.IO) {
            getUpcomingMovies()
        }
        popularJob = viewModelScope.launch(Dispatchers.IO) {
            getPopularMovies()
        }
        topRatedJob = viewModelScope.launch(Dispatchers.IO) {
            getTopMovies()
        }


    }

    suspend fun getTopMovies() {
        interactors.getTopMovies().collect {
            _topMovies.postValue(it)
        }
    }

    suspend fun getPopularMovies() {
        interactors.getPopular(viewModelScope).collect {
            _popularMovies.postValue(it)
        }
    }

    suspend fun getNowPlayingMovies() {
        interactors.getNowPlaying(viewModelScope).collect {
            _nowPlayingMovies.postValue(it)
        }
    }

    suspend fun getUpcomingMovies() {
        interactors.getUpcoming(viewModelScope).collect {
            _upcomingMovies.postValue(it)
        }
    }

}