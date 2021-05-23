package com.andika.architecturecomponent.framework.presentation.home.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.interactors.MovieInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel
@Inject constructor(
    private val interactors: MovieInteractors
) : ViewModel() {
    var movieJob: Job? = null
    var tvJob: Job? = null
    var favouriteMovies: MutableLiveData<DataState<PagingData<LocalMovie>>> = MutableLiveData()
    var favouriteTV: MutableLiveData<DataState<PagingData<LocalTV>>> = MutableLiveData()


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

    suspend fun getFavTV() {
        interactors.getAllFavouriteTV(viewModelScope).collect {
            favouriteTV.postValue(it)
        }
    }

    suspend fun getFavMovie() {
        interactors.getAllFavouriteMovie(viewModelScope).collect {
            favouriteMovies.postValue(it)
        }
    }


}
