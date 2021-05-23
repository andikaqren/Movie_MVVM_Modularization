package com.andika.architecturecomponent.framework.presentation.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies
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
class MovieViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule =
        TestCoroutineRule()


    @Mock
    private lateinit var movieViewModel: MovieViewModel

    @Mock
    private lateinit var movieInteractors: com.andika.architecturecomponent.core.business.interactors.MovieInteractors


    @Mock
    private lateinit var topObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovies>>

    @Mock
    private lateinit var nowPlayingObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>>>

    @Mock
    private lateinit var popularObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>>>

    @Mock
    private lateinit var upcomingObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>>>

    private var dummyDataPager = com.andika.architecturecomponent.core.business.domain.utils.Helper.getDummyMoviePager()
    private var dummyData = com.andika.architecturecomponent.core.business.domain.utils.Helper.getDummyMovies()

    private var dummyError = Exception()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(coroutineRule.dispatcher)
        movieViewModel =
            MovieViewModel(
                movieInteractors
            )
                .apply {
                    topMovies.observeForever(topObserver)
                    upcomingMovies.observeForever(upcomingObserver)
                    nowPlayingMovies.observeForever(nowPlayingObserver)
                    popularMovies.observeForever(popularObserver)
                }
    }

    @Test
    fun `get list movies data should be error`() {
        coroutineRule.runBlockingTest {
            testGetErrorTopMovies()
            testGetErrorNowPlayingMovies()
            testGetErrorPopularMovies()
            testGetErrorUpcomingMovies()
        }
    }

    @Test
    fun `get list movies data should be success`() {
        coroutineRule.runBlockingTest {
            testGetTopMovies()
            testGetNowPlayingMovies()
            testGetPopularMovies()
            testGetUpcomingMovies()
        }
    }

    @Test
    fun testGetTopMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getTopMovies()).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyData))
            })
            movieViewModel.getTopMovies()
            verify(movieInteractors).getTopMovies()
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyData))
        }
    }

    @Test
    fun testGetErrorTopMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getTopMovies()).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            movieViewModel.getTopMovies()
            verify(movieInteractors).getTopMovies()
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(topObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetNowPlayingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getNowPlaying(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
            })
            movieViewModel.getNowPlayingMovies()
            verify(movieInteractors).getNowPlaying(movieViewModel.viewModelScope)
            verify(nowPlayingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(nowPlayingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
        }
    }

    @Test
    fun testGetErrorNowPlayingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getNowPlaying(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            movieViewModel.getNowPlayingMovies()
            verify(movieInteractors).getNowPlaying(movieViewModel.viewModelScope)
            verify(nowPlayingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(nowPlayingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetPopularMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getPopular(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
            })
            movieViewModel.getPopularMovies()
            verify(movieInteractors).getPopular(movieViewModel.viewModelScope)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
        }
    }

    @Test
    fun testGetErrorPopularMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getPopular(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            movieViewModel.getPopularMovies()
            verify(movieInteractors).getPopular(movieViewModel.viewModelScope)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(popularObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetUpcomingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getUpcoming(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))
            })
            movieViewModel.getUpcomingMovies()
            verify(movieInteractors).getUpcoming(movieViewModel.viewModelScope)
            verify(upcomingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(upcomingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyDataPager))

        }
    }

    @Test
    fun testGetErrorUpcomingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getUpcoming(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))
            })
            movieViewModel.getUpcomingMovies()
            verify(movieInteractors).getUpcoming(movieViewModel.viewModelScope)
            verify(upcomingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            verify(upcomingObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Error(dummyError))

        }
    }


}

