package com.andika.architecturecomponent.framework.presentation.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.TV
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import com.andika.architecturecomponent.core.business.interactors.MovieUseCase
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val interactors: MovieUseCase
) : ViewModel() {
    private var _similarMovies: MutableLiveData<DataState<Movies>> = MutableLiveData()
    private var _recomendationMovies: MutableLiveData<DataState<Movies>> = MutableLiveData()
    private var _similarTV: MutableLiveData<DataState<TVs>> = MutableLiveData()
    private var _recomendationTV: MutableLiveData<DataState<TVs>> = MutableLiveData()
    private var _favMovie: MutableLiveData<DataState<Movie>> = MutableLiveData()
    private var _favTV: MutableLiveData<DataState<TV>> = MutableLiveData()
    private var _tv: MutableLiveData<DataState<TV>> = MutableLiveData()
    private var _movie: MutableLiveData<DataState<Movie>> = MutableLiveData()
    private var _dynamicLink = MutableLiveData<DataState<String>>()
    var similarMovies: LiveData<DataState<Movies>> = _similarMovies
    var recomendationMovies: LiveData<DataState<Movies>> = _recomendationMovies
    var similarTV: LiveData<DataState<TVs>> = _similarTV
    var recomendationTV: LiveData<DataState<TVs>> = _recomendationTV
    var favMovie: LiveData<DataState<Movie>> = _favMovie
    var favTV: LiveData<DataState<TV>> = _favTV
    var tv: LiveData<DataState<TV>> = _tv
    var movie: LiveData<DataState<Movie>> = _movie
    var dynamicLink: LiveData<DataState<String>> = _dynamicLink

    private var similarJob: Job? = null
    private var recomendationJob: Job? = null
    private var favJob: Job? = null


    fun reloadData(filename: Int) {
        cancelReloadJob()
        favJob = viewModelScope.launch(Dispatchers.IO) {
            getSelectedMovie(filename)
        }
        similarJob = viewModelScope.launch(Dispatchers.IO) {
            getSimilarMovie(filename)
        }
        recomendationJob = viewModelScope.launch(Dispatchers.IO) {
            getRecomendationMovie(filename)
        }
    }

    fun reloadDataTV(filename: Int) {
        cancelReloadJob()
        favJob = viewModelScope.launch(Dispatchers.IO) {
            getSelectedTV(filename)
        }
        similarJob = viewModelScope.launch(Dispatchers.IO) {
            getSimilarTV(filename)
        }
        recomendationJob = viewModelScope.launch(Dispatchers.IO) {
            getRecomendationTV(filename)
        }
    }

    private fun cancelReloadJob() {
        favJob?.cancel()
        recomendationJob?.cancel()
        similarJob?.cancel()
    }

    private suspend fun getRecomendationTV(filename: Int) {
        interactors.getRecomendationTV(filename).collect {
            _recomendationTV.postValue(it)
        }
    }

    private suspend fun getSimilarTV(filename: Int) {
        interactors.getSimilarTV(filename).collect {
            _similarTV.postValue(it)
        }
    }


    private suspend fun getSelectedTV(filename: Int) {
        interactors.getSelectedTV(filename).collect {
            _favTV.postValue(it)
        }
    }


    private suspend fun getRecomendationMovie(filename: Int) {
        interactors.getRecomendation(filename).collect {
            _recomendationMovies.postValue(it)
        }
    }

    private suspend fun getSimilarMovie(filename: Int) {
        interactors.getSimilar(filename).collect {
            _similarMovies.postValue(it)
        }
    }


    private suspend fun getSelectedMovie(filename: Int) {
        interactors.getSelectedMovie(filename).collect {
            _favMovie.postValue(it)
        }
    }


    fun setFav(isFavourite: Boolean, type: String, data: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                MOVIE -> {
                    selectFavMovie(isFavourite, data as Movie)
                }
                TV -> {
                    selectFavTV(isFavourite, data as TV)
                }
            }
        }
    }

    private suspend fun selectFavTV(
        isFavourite: Boolean,
        localTV: TV
    ) {
        if (isFavourite) interactors.insertSelectedTV(localTV)
        else interactors.removeSelectedTV(localTV)
    }

    private suspend fun selectFavMovie(
        isFavourite: Boolean,
        localMovie: Movie
    ) {
        if (isFavourite) interactors.insertSelectedMovie(localMovie)
        else interactors.removeSelectedMovie(localMovie)
    }

    suspend fun getMoviesDetail(id: String) {
        interactors.getDetailMovie(id).collect {
            _movie.postValue(it)
        }
    }

    suspend fun getTvDetail(id: String) {
        interactors.getDetailTV(id).collect {
            _tv.postValue(it)
        }
    }

    fun getDynamicLink(deepLink: String) {
        _dynamicLink.postValue(DataState.Loading)
        Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse(deepLink)
            domainUriPrefix = deepLink
            androidParameters("com.andika.architecturecomponent") {

            }
        }.addOnSuccessListener {
            _dynamicLink.postValue(DataState.Success(it.shortLink.toString()))
        }.addOnFailureListener {
            _dynamicLink.postValue(DataState.Error(it))
        }
    }


}
