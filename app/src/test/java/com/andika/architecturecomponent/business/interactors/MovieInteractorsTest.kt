package com.andika.architecturecomponent.business.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.data.local.LocalDataSource
import com.andika.architecturecomponent.business.data.remote.RemoteDataSource
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovies
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.domain.utils.*
import com.andika.architecturecomponent.helper.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito

import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class MovieInteractorsTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule =
        TestCoroutineRule()


    @Mock
    private lateinit var movieInteractors: FakeMovieInteractors


    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource


    @Mock
    private lateinit var topMovieObserver: Observer<DataState<RemoteMovies>>

    @Mock
    private lateinit var nowPlayingMovieObserver: Observer<DataState<PagingData<RemoteMovie>>>

    @Mock
    private lateinit var upcomingMovieObserver: Observer<DataState<PagingData<RemoteMovie>>>

    @Mock
    private lateinit var popularMovieObserver: Observer<DataState<PagingData<RemoteMovie>>>

    @Mock
    private lateinit var favMovieObserver: Observer<DataState<RemoteMovie>>

    @Mock
    private lateinit var favTVObserver: Observer<DataState<RemoteTV>>

    @Mock
    private lateinit var topTVObserver: Observer<DataState<RemoteTVs>>

    @Mock
    private lateinit var latestTVObserver: Observer<DataState<PagingData<RemoteTV>>>

    @Mock
    private lateinit var popularTVObserver: Observer<DataState<PagingData<RemoteTV>>>

    private var dummyMovie = Helper.getDummyMovies()


    private var dummyTV = Helper.getDummyTVs()
    private val dummyLocalTV = dummyTV.results[0].toLocalTV()
    private val dummyLocalMovie = dummyMovie.results[0].toLocalMovie()


    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(coroutineRule.dispatcher)
        movieInteractors = FakeMovieInteractors(
            networkDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @Test
    fun `get list movies & tv data should be success`() {
        coroutineRule.runBlockingTest {
            testGetTopMovies()
            testGetNowPlayingMovies()
            testGetPopularMovies()
            testGetUpcomingMovies()
            testGetTopTV()
            testGetNowPlayingTV()
            testGetPopularTV()
        }
    }

    @Test
    fun `add or remove list favourite movies & tv data should be success`() {
        coroutineRule.runBlockingTest {
            testAddAndRemoveFavouriteTV()
            testGetFavouriteTV()
            testAddAndRemoveFavouriteMovie()
            testGetFavouriteMovie()
        }
    }

    @Test
    fun `get list favourite movies & tv data should be success`() {
        coroutineRule.runBlockingTest {
          testGetFavouriteMovie()
          testGetFavouriteTV()
        }
    }

    @Test
    fun testAddAndRemoveFavouriteTV() {
        coroutineRule.dispatcher.runBlockingTest {
            movieInteractors.insertSelectedTV(dummyLocalTV)
            Mockito.verify(localDataSource).insertSelectedTV(dummyLocalTV)
            movieInteractors.removeSelectedTV(dummyLocalTV)
            Mockito.verify(localDataSource).removeSelectedTV(dummyLocalTV)
        }
    }

    @Test
    fun testGetFavouriteTV() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(localDataSource.getSelectedTV(anyInt())).thenReturn(dummyLocalTV)
            movieInteractors.getSelectedTV(dummyLocalTV.id).asLiveData().observeForever(favTVObserver)
            Mockito.verify(localDataSource).getSelectedTV(anyInt())
            Mockito.verify(favTVObserver).onChanged(DataState.Loading)
            Mockito.verify(favTVObserver).onChanged(DataState.Success(dummyLocalTV.toTV()))

        }
    }

    @Test
    fun testGetFavouriteMovie() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(localDataSource.getSelectedMovie(anyInt())).thenReturn(dummyLocalMovie)
            movieInteractors.getSelectedMovie(dummyLocalTV.id).asLiveData().observeForever(favMovieObserver)
            Mockito.verify(localDataSource).getSelectedMovie(anyInt())
            Mockito.verify(favMovieObserver).onChanged(DataState.Loading)
            Mockito.verify(favMovieObserver).onChanged(DataState.Success(dummyLocalMovie.toMovie()))
        }
    }

    @Test
    fun testAddAndRemoveFavouriteMovie() {
        coroutineRule.dispatcher.runBlockingTest {
            movieInteractors.insertSelectedMovie(dummyLocalMovie)
            Mockito.verify(localDataSource).insertSelectedMovie(dummyLocalMovie)
            movieInteractors.removeSelectedMovie(dummyLocalMovie)
            Mockito.verify(localDataSource).removeSelectedMovie(dummyLocalMovie)
        }
    }


    @Test
    fun testGetTopMovies() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getTopMovies(anyInt())).thenReturn(dummyMovie)
            movieInteractors.getTopMovies().asLiveData().observeForever(topMovieObserver)
            Mockito.verify(remoteDataSource).getTopMovies(1)
            Mockito.verify(topMovieObserver).onChanged(DataState.Loading)
            Mockito.verify(topMovieObserver).onChanged(DataState.Success(dummyMovie))
        }
    }


    @Test
    fun testGetNowPlayingMovies() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getNowPlaying(anyInt())).thenReturn(dummyMovie)
            movieInteractors.getNowPlayingMovies().asLiveData()
                .observeForever(nowPlayingMovieObserver)
            assertThat(nowPlayingMovieObserver, `is`(notNullValue()))
        }
    }

    @Test
    fun testGetPopularMovies() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getPopular(anyInt())).thenReturn(dummyMovie)
            movieInteractors.getPopular().asLiveData().observeForever(popularMovieObserver)
            assertThat(popularMovieObserver, `is`(notNullValue()))
        }
    }

    @Test
    fun testGetUpcomingMovies() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getUpcoming(anyInt())).thenReturn(dummyMovie)
            movieInteractors.getUpcoming().asLiveData().observeForever(upcomingMovieObserver)
            assertThat(upcomingMovieObserver, `is`(notNullValue()))
        }
    }

    @Test
    fun testGetTopTV() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getTopRatedTV()).thenReturn(dummyTV)
            movieInteractors.getTopRatedTv().asLiveData().observeForever(topTVObserver)
            Mockito.verify(remoteDataSource).getTopRatedTV()
            Mockito.verify(topTVObserver).onChanged(DataState.Loading)
            Mockito.verify(topTVObserver).onChanged(DataState.Success(dummyTV))
        }
    }

    @Test
    fun testGetNowPlayingTV() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getLatestTV(anyInt())).thenReturn(dummyTV)
            movieInteractors.getLatestTV().asLiveData().observeForever(latestTVObserver)
            assertThat(latestTVObserver, `is`(notNullValue()))
        }
    }


    @Test
    fun testGetPopularTV() {
        coroutineRule.dispatcher.runBlockingTest {
            Mockito.`when`(remoteDataSource.getPopularTV(anyInt())).thenReturn(dummyTV)
            movieInteractors.getPopularTv().asLiveData().observeForever(popularTVObserver)
            assertThat(popularTVObserver, `is`(notNullValue()))
        }
    }


}