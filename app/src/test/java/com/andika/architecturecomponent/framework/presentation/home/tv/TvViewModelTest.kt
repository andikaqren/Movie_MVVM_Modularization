package com.andika.architecturecomponent.framework.presentation.home.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.Helper
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import com.andika.architecturecomponent.helper.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class TvViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule =
        TestCoroutineRule()


    @Mock
    private lateinit var tvViewModel: TvViewModel

    @Mock
    private lateinit var movieInteractors: com.andika.architecturecomponent.core.business.interactors.MovieInteractors

    @Mock
    private lateinit var topObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTVs>>

    @Mock
    private lateinit var latestObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>>>

    @Mock
    private lateinit var popularObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>>>

    private var dummyData = com.andika.architecturecomponent.core.business.domain.utils.Helper.getDummyTVs()
    private var dummyDataPager = com.andika.architecturecomponent.core.business.domain.utils.Helper.getDummyTVPager()

    private var dummyError = Exception()


    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(coroutineRule.dispatcher)
        tvViewModel =
            TvViewModel(
                movieInteractors
            ).apply {
                topTV.observeForever(topObserver)
                latestTV.observeForever(latestObserver)
                popularTV.observeForever(popularObserver)
            }
    }

    @Test
    fun `get list tv data should be error`() {
        coroutineRule.runBlockingTest {
            testGetErrorTopTV()
            testGetErrorNowPlayingTV()
            testGetErrorPopularTV()
        }
    }


    @Test
    fun `get list tv data should be success`() {
        coroutineRule.runBlockingTest {
            testGetTopTV()
            testGetNowPlayingTV()
            testGetPopularTV()
        }
    }

    @Test
    fun testGetTopTV() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getTopRatedTv()).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyData))
            })
            tvViewModel.getTopRatedTV()
            verify(movieInteractors).getTopRatedTv()
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyData))

        }
    }

    @Test
    fun testGetErrorTopTV() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getTopRatedTv()).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            tvViewModel.getTopRatedTV()
            verify(movieInteractors).getTopRatedTv()
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetNowPlayingTV() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getLatestTV(tvViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
            })
            tvViewModel.getLatestTV()
            verify(movieInteractors).getLatestTV(tvViewModel.viewModelScope)
            verify(latestObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(latestObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
        }
    }

    @Test
    fun testGetErrorNowPlayingTV() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getLatestTV(tvViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            tvViewModel.getLatestTV()
            verify(movieInteractors).getLatestTV(tvViewModel.viewModelScope)
            verify(latestObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(latestObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetPopularTV() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getPopularTv(tvViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
            })
            tvViewModel.getPopularTV()
            verify(movieInteractors).getPopularTv(tvViewModel.viewModelScope)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
        }
    }

    @Test
    fun testGetErrorPopularTV() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getPopularTv(tvViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            tvViewModel.getPopularTV()
            verify(movieInteractors).getPopularTv(tvViewModel.viewModelScope)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }

}

