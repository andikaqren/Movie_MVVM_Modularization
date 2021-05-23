package com.andika.architecturecomponent.framework.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.business.data.remote.model.RemoteMovies
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.data.remote.model.RemoteTVs
import com.andika.architecturecomponent.business.domain.state.DataState
import com.andika.architecturecomponent.business.domain.utils.Helper
import com.andika.architecturecomponent.business.domain.utils.toLocalMovie
import com.andika.architecturecomponent.business.domain.utils.toLocalTV
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule =
        TestCoroutineRule()

    @Mock
    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private lateinit var movieInteractors: MovieInteractors

    @Mock
    private lateinit var tvObserver: Observer<DataState<RemoteTV>>

    @Mock
    private lateinit var movieObserver: Observer<DataState<RemoteMovie>>

    @Mock
    private lateinit var recomendationTVsObserver: Observer<DataState<RemoteTVs>>

    @Mock
    private lateinit var similarTVsObserver: Observer<DataState<RemoteTVs>>

    @Mock
    private lateinit var recomendationMoviesObserver: Observer<DataState<RemoteMovies>>

    @Mock
    private lateinit var similarMoviesObserver: Observer<DataState<RemoteMovies>>

    @Mock
    private lateinit var favTVObserver: Observer<DataState<RemoteTV>>

    @Mock
    private lateinit var favMovieObserver: Observer<DataState<RemoteMovie>>

    private val dummyTVs = Helper.getDummyTVs()
    private val dummyLocalTV = dummyTVs.results[0].toLocalTV()
    private val dummyTV = dummyTVs.results[0]
    private val dummyMovies = Helper.getDummyMovies()
    private val dummyMovie = dummyMovies.results[0]
    private val dummyLocalMovie = dummyMovies.results[0].toLocalMovie()
    private val dummyId = 10


    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(coroutineRule.dispatcher)
        detailViewModel =
            DetailViewModel(
                movieInteractors
            ).apply {
                movie.observeForever(movieObserver)
                tv.observeForever(tvObserver)
                recomendationMovies.observeForever(recomendationMoviesObserver)
                recomendationTV.observeForever(recomendationTVsObserver)
                similarTV.observeForever(similarTVsObserver)
                similarMovies.observeForever(similarMoviesObserver)
                favMovie.observeForever(favMovieObserver)
                favTV.observeForever(favTVObserver)
            }
    }

    @Test
    fun `reload data should be success`() {
        testGetRecomendationMovies()
        testGetRecomendationTVs()
        testGetSimilarMovies()
        testGetSimilarTVs()
        testGetFavTV()
        testGetFavMovie()
    }

    @Test
    fun `get details data should be success`() {
        testGetDetailMovie()
        testGetDetailTV()
    }

    @Test
    fun `insert and remove fav movie & tv`() {
        testInsertFavTV()
        testInsertFavMovie()
    }

    @Test
    fun testInsertFavTV() {
        coroutineRule.runBlockingTest {
            detailViewModel.selectFavTV(true, dummyLocalTV)
            Mockito.verify(movieInteractors).insertSelectedTV(dummyLocalTV)
            detailViewModel.selectFavTV(false, dummyLocalTV)
            Mockito.verify(movieInteractors).removeSelectedTV(dummyLocalTV)
        }
    }

    @Test
    fun testInsertFavMovie() {
        coroutineRule.runBlockingTest {
            detailViewModel.selectFavMovie(true, dummyLocalMovie)
            Mockito.verify(movieInteractors).insertSelectedMovie(dummyLocalMovie)
            detailViewModel.selectFavMovie(false, dummyLocalMovie)
            Mockito.verify(movieInteractors).removeSelectedMovie(dummyLocalMovie)
        }
    }

    @Test
    fun testGetDetailTV() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getDetailTV(dummyId.toString())).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyTV))
                })
            detailViewModel.getTvDetail(dummyId.toString())
            Mockito.verify(movieInteractors).getDetailTV(dummyId.toString())
            Mockito.verify(tvObserver).onChanged(DataState.Loading)
            Mockito.verify(tvObserver).onChanged(DataState.Success(dummyTV))
        }
    }

    @Test
    fun testGetDetailMovie() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getDetailMovie(dummyId.toString())).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyMovie))
                })
            detailViewModel.getMoviesDetail(dummyId.toString())
            Mockito.verify(movieInteractors).getDetailMovie(dummyId.toString())
            Mockito.verify(movieObserver).onChanged(DataState.Loading)
            Mockito.verify(movieObserver).onChanged(DataState.Success(dummyMovie))
        }
    }

    @Test
    fun testGetFavTV() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getSelectedTV(dummyId)).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyTV))
                })
            detailViewModel.getSelectedTV(dummyId)
            Mockito.verify(movieInteractors).getSelectedTV(dummyId)
            Mockito.verify(favTVObserver).onChanged(DataState.Loading)
            Mockito.verify(favTVObserver).onChanged(DataState.Success(dummyTV))

        }
    }

    @Test
    fun testGetFavMovie() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getSelectedMovie(dummyId)).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyMovie))
                })
            detailViewModel.getSelectedMovie(dummyId)
            Mockito.verify(movieInteractors).getSelectedMovie(dummyId)
            Mockito.verify(favMovieObserver).onChanged(DataState.Loading)
            Mockito.verify(favMovieObserver).onChanged(DataState.Success(dummyMovie))
        }
    }

    @Test
    fun testGetRecomendationMovies() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getRecomendation(dummyId)).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyMovies))
                })
            detailViewModel.getRecomendationMovie(dummyId)
            Mockito.verify(movieInteractors).getRecomendation(dummyId)
            Mockito.verify(recomendationMoviesObserver).onChanged(DataState.Loading)
            Mockito.verify(recomendationMoviesObserver).onChanged(DataState.Success(dummyMovies))
        }
    }

    @Test
    fun testGetRecomendationTVs() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getRecomendationTV(dummyId)).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyTVs))
                })
            detailViewModel.getRecomendationTV(dummyId)
            Mockito.verify(movieInteractors).getRecomendationTV(dummyId)
            Mockito.verify(recomendationTVsObserver).onChanged(DataState.Loading)
            Mockito.verify(recomendationTVsObserver).onChanged(DataState.Success(dummyTVs))
        }
    }

    @Test
    fun testGetSimilarMovies() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getSimilar(dummyId)).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyMovies))
                })
            detailViewModel.getSimilarMovie(dummyId)
            Mockito.verify(movieInteractors).getSimilar(dummyId)
            Mockito.verify(recomendationMoviesObserver).onChanged(DataState.Loading)
            Mockito.verify(recomendationMoviesObserver).onChanged(DataState.Success(dummyMovies))
        }
    }

    @Test
    fun testGetSimilarTVs() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getSimilarTV(dummyId)).thenReturn(
                flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyTVs))
                })
            detailViewModel.getSimilarTV(dummyId)
            Mockito.verify(movieInteractors).getSimilarTV(dummyId)
            Mockito.verify(similarTVsObserver).onChanged(DataState.Loading)
            Mockito.verify(similarTVsObserver).onChanged(DataState.Success(dummyTVs))
        }
    }


}
