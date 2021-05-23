package com.andika.architecturecomponent.framework.presentation.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovies
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.business.domain.utils.AppConstant.TV
import com.andika.architecturecomponent.business.domain.utils.toLocalMovie
import com.andika.architecturecomponent.business.domain.utils.toLocalTV
import com.andika.architecturecomponent.business.interactors.MovieInteractors
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
    private val interactors: MovieInteractors
) : ViewModel() {
    private var _similarMovies: MutableLiveData<DataState<RemoteMovies>> = MutableLiveData()
    private var _recomendationMovies: MutableLiveData<DataState<RemoteMovies>> = MutableLiveData()
    private var _similarTV: MutableLiveData<DataState<RemoteTVs>> = MutableLiveData()
    private var _recomendationTV: MutableLiveData<DataState<RemoteTVs>> = MutableLiveData()
    private var _favMovie: MutableLiveData<DataState<RemoteMovie>> = MutableLiveData()
    private var _favTV: MutableLiveData<DataState<RemoteTV>> = MutableLiveData()
    private var _tv: MutableLiveData<DataState<RemoteTV>> = MutableLiveData()
    private var _movie: MutableLiveData<DataState<RemoteMovie>> = MutableLiveData()
    private var _dynamicLink = MutableLiveData<DataState<String>>()
    var similarMovies: LiveData<DataState<RemoteMovies>> = _similarMovies
    var recomendationMovies: LiveData<DataState<RemoteMovies>> = _recomendationMovies
    var similarTV: LiveData<DataState<RemoteTVs>> = _similarTV
    var recomendationTV: LiveData<DataState<RemoteTVs>> = _recomendationTV
    var favMovie: LiveData<DataState<RemoteMovie>> = _favMovie
    var favTV: LiveData<DataState<RemoteTV>> = _favTV
    var tv: LiveData<DataState<RemoteTV>> = _tv
    var movie: LiveData<DataState<RemoteMovie>> = _movie
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

    fun cancelReloadJob() {
        favJob?.cancel()
        recomendationJob?.cancel()
        similarJob?.cancel()
    }

    suspend fun getRecomendationTV(filename: Int) {
        interactors.getRecomendationTV(filename).collect {
            _recomendationTV.postValue(it)
        }
    }

    suspend fun getSimilarTV(filename: Int) {
        interactors.getSimilarTV(filename).collect {
            _similarTV.postValue(it)
        }
    }


    suspend fun getSelectedTV(filename: Int) {
        interactors.getSelectedTV(filename).collect {
            _favTV.postValue(it)
        }
    }


    suspend fun getRecomendationMovie(filename: Int) {
        interactors.getRecomendation(filename).collect {
            _recomendationMovies.postValue(it)
        }
    }

    suspend fun getSimilarMovie(filename: Int) {
        interactors.getSimilar(filename).collect {
            _similarMovies.postValue(it)
        }
    }


    suspend fun getSelectedMovie(filename: Int) {
        interactors.getSelectedMovie(filename).collect {
            _favMovie.postValue(it)
        }
    }


    fun setFav(isFavourite: Boolean, type: String, data: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                MOVIE -> {
                    selectFavMovie(isFavourite, (data as RemoteMovie).toLocalMovie())
                }
                TV -> {
                    selectFavTV(isFavourite, (data as RemoteTV).toLocalTV())
                }
            }
        }
    }

    suspend fun selectFavTV(isFavourite: Boolean, localTV: LocalTV) {
        if (isFavourite) interactors.insertSelectedTV(localTV)
        else interactors.removeSelectedTV(localTV)
    }

    suspend fun selectFavMovie(isFavourite: Boolean, localMovie: LocalMovie) {
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
