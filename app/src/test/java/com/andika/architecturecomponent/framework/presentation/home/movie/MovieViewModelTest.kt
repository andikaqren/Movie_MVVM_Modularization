package com.andika.architecturecomponent.framework.presentation.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovies
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.domain.utils.Helper
import com.andika.architecturecomponent.business.interactors.MovieInteractors
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
    private lateinit var movieInteractors: MovieInteractors


    @Mock
    private lateinit var topObserver: Observer<DataState<RemoteMovies>>

    @Mock
    private lateinit var nowPlayingObserver: Observer<DataState<PagingData<RemoteMovie>>>

    @Mock
    private lateinit var popularObserver: Observer<DataState<PagingData<RemoteMovie>>>

    @Mock
    private lateinit var upcomingObserver: Observer<DataState<PagingData<RemoteMovie>>>

    private var dummyDataPager = Helper.getDummyMoviePager()
    private var dummyData = Helper.getDummyMovies()

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
                emit(DataState.Loading)
                emit(DataState.Success(dummyData))
            })
            movieViewModel.getTopMovies()
            verify(movieInteractors).getTopMovies()
            verify(topObserver).onChanged(DataState.Loading)
            verify(topObserver).onChanged(DataState.Success(dummyData))
        }
    }

    @Test
    fun testGetErrorTopMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getTopMovies()).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Error(dummyError))
            })
            movieViewModel.getTopMovies()
            verify(movieInteractors).getTopMovies()
            verify(topObserver).onChanged(DataState.Loading)
            verify(topObserver).onChanged(DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetNowPlayingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getNowPlaying(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Success(dummyDataPager))
            })
            movieViewModel.getNowPlayingMovies()
            verify(movieInteractors).getNowPlaying(movieViewModel.viewModelScope)
            verify(nowPlayingObserver).onChanged(DataState.Loading)
            verify(nowPlayingObserver).onChanged(DataState.Success(dummyDataPager))
        }
    }

    @Test
    fun testGetErrorNowPlayingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getNowPlaying(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Error(dummyError))
            })
            movieViewModel.getNowPlayingMovies()
            verify(movieInteractors).getNowPlaying(movieViewModel.viewModelScope)
            verify(nowPlayingObserver).onChanged(DataState.Loading)
            verify(nowPlayingObserver).onChanged(DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetPopularMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getPopular(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Success(dummyDataPager))
            })
            movieViewModel.getPopularMovies()
            verify(movieInteractors).getPopular(movieViewModel.viewModelScope)
            verify(popularObserver).onChanged(DataState.Loading)
            verify(popularObserver).onChanged(DataState.Success(dummyDataPager))
        }
    }

    @Test
    fun testGetErrorPopularMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getPopular(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Error(dummyError))
            })
            movieViewModel.getPopularMovies()
            verify(movieInteractors).getPopular(movieViewModel.viewModelScope)
            verify(popularObserver).onChanged(DataState.Loading)
            verify(popularObserver).onChanged(DataState.Error(dummyError))

        }
    }

    @Test
    fun testGetUpcomingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getUpcoming(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Success(dummyDataPager))
            })
            movieViewModel.getUpcomingMovies()
            verify(movieInteractors).getUpcoming(movieViewModel.viewModelScope)
            verify(upcomingObserver).onChanged(DataState.Loading)
            verify(upcomingObserver).onChanged(DataState.Success(dummyDataPager))

        }
    }

    @Test
    fun testGetErrorUpcomingMovies() {
        coroutineRule.runBlockingTest {
            `when`(movieInteractors.getUpcoming(movieViewModel.viewModelScope)).thenReturn(flow {
                emit(DataState.Loading)
                emit(DataState.Error(dummyError))
            })
            movieViewModel.getUpcomingMovies()
            verify(movieInteractors).getUpcoming(movieViewModel.viewModelScope)
            verify(upcomingObserver).onChanged(DataState.Loading)
            verify(upcomingObserver).onChanged(DataState.Error(dummyError))

        }
    }


}

