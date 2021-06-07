package com.andika.architecturecomponent.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import com.andika.architecturecomponent.core.business.interactors.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavouriteViewModel : ViewModel() {
    private lateinit var interactors: MovieUseCase
    private var movieJob: Job? = null
    private var tvJob: Job? = null
    var favouriteMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var favouriteTV: MutableLiveData<DataState<PagingData<TV>>> = MutableLiveData()


    fun setApi(interactors: MovieUseCase) {
        this.interactors = interactors
    }

    fun reloadData() {
        movieJob?.cancel()
        tvJob?.cancel()
        movieJob = viewModelScope.launch(Dispatchers.IO) {
            getFavMovie()
        }
        tvJob = viewModelScope.launch(Dispatchers.IO) {
            getFavTV()
        }

    }

    private suspend fun getFavTV() {
        interactors.getAllFavouriteTV(viewModelScope).collect {
            favouriteTV.postValue(it)
        }
    }

    private suspend fun getFavMovie() {
        interactors.getAllFavouriteMovie(viewModelScope).collect {
            favouriteMovies.postValue(it)
        }
    }


}
