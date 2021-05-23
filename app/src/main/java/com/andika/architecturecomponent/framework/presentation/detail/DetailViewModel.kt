package com.andika.architecturecomponent.framework.presentation.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andika.architecturecomponent.core.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.core.business.data.local.model.LocalTV
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.TV
import com.andika.architecturecomponent.business.domain.utils.toLocalMovie
import com.andika.architecturecomponent.business.domain.utils.toLocalTV
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val interactors: com.andika.architecturecomponent.core.business.interactors.MovieInteractors
) : ViewModel() {
    private var _similarMovies: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies>> = MutableLiveData()
    private var _recomendationMovies: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies>> = MutableLiveData()
    private var _similarTV: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = MutableLiveData()
    private var _recomendationTV: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = MutableLiveData()
    private var _favMovie: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>> = MutableLiveData()
    private var _favTV: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>> = MutableLiveData()
    private var _tv: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>> = MutableLiveData()
    private var _movie: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>> = MutableLiveData()
    private var _dynamicLink = MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<String>>()
    var similarMovies: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies>> = _similarMovies
    var recomendationMovies: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies>> = _recomendationMovies
    var similarTV: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = _similarTV
    var recomendationTV: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = _recomendationTV
    var favMovie: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>> = _favMovie
    var favTV: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>> = _favTV
    var tv: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>> = _tv
    var movie: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>> = _movie
    var dynamicLink: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<String>> = _dynamicLink

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
                    selectFavMovie(isFavourite, (data as com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie).toLocalMovie())
                }
                TV -> {
                    selectFavTV(isFavourite, (data as com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV).toLocalTV())
                }
            }
        }
    }

    suspend fun selectFavTV(isFavourite: Boolean, localTV: com.andika.architecturecomponent.core.business.data.local.model.LocalTV) {
        if (isFavourite) interactors.insertSelectedTV(localTV)
        else interactors.removeSelectedTV(localTV)
    }

    suspend fun selectFavMovie(isFavourite: Boolean, localMovie: com.andika.architecturecomponent.core.business.data.local.model.LocalMovie) {
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
        _dynamicLink.postValue(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
        Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse(deepLink)
            domainUriPrefix = deepLink
            androidParameters("com.andika.architecturecomponent") {

            }
        }.addOnSuccessListener {
            _dynamicLink.postValue(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(it.shortLink.toString()))
        }.addOnFailureListener {
            _dynamicLink.postValue(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(it))
        }
    }


}
