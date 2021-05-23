package com.andika.architecturecomponent.framework.presentation.home.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.interactors.MovieInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel
@Inject constructor(
    private val interactors: MovieInteractors
) : ViewModel() {
    private var latestJob: Job? = null
    private var topJob: Job? = null
    private var popularJob: Job? = null
    private var _topTV: MutableLiveData<DataState<RemoteTVs>> = MutableLiveData()
    private var _popularTV: MutableLiveData<DataState<PagingData<RemoteTV>>> = MutableLiveData()
    private var _latestTV: MutableLiveData<DataState<PagingData<RemoteTV>>> = MutableLiveData()
    var topTV: LiveData<DataState<RemoteTVs>> = _topTV
    var popularTV: LiveData<DataState<PagingData<RemoteTV>>> = _popularTV
    var latestTV: LiveData<DataState<PagingData<RemoteTV>>> = _latestTV

    fun reloadData() {
        latestJob?.cancel()
        popularJob?.cancel()
        topJob?.cancel()
        latestJob = viewModelScope.launch(Dispatchers.IO) {
            getLatestTV()
        }
        popularJob = viewModelScope.launch(Dispatchers.IO) {
            getPopularTV()
        }
        topJob = viewModelScope.launch(Dispatchers.IO) {
            getTopRatedTV()
        }
    }


    suspend fun getTopRatedTV() {
        interactors.getTopRatedTv().collect {
            _topTV.postValue(it)
        }
    }

    suspend fun getPopularTV() {
        interactors.getPopularTv(viewModelScope).collect {
            _popularTV.postValue(it)
        }
    }

    suspend fun getLatestTV() {
        interactors.getLatestTV(viewModelScope).collect {
            _latestTV.postValue(it)
        }
    }
}