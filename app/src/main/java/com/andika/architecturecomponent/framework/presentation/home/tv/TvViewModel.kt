package com.andika.architecturecomponent.framework.presentation.home.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel
@Inject constructor(
    private val interactors: com.andika.architecturecomponent.core.business.interactors.MovieInteractors
) : ViewModel() {
    private var latestJob: Job? = null
    private var topJob: Job? = null
    private var popularJob: Job? = null
    private var _topTV: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = MutableLiveData()
    private var _popularTV: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>>> = MutableLiveData()
    private var _latestTV: MutableLiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>>> = MutableLiveData()
    var topTV: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>> = _topTV
    var popularTV: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>>> = _popularTV
    var latestTV: LiveData<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>>> = _latestTV

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