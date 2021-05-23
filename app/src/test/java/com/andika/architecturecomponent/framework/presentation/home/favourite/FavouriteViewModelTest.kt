package com.andika.architecturecomponent.framework.presentation.home.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.business.data.local.model.LocalTV
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class FavouriteViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule =
        TestCoroutineRule()

    @Mock
    private lateinit var favViewModel: FavouriteViewModel

    @Mock
    private lateinit var movieInteractors: MovieInteractors

    @Mock
    private lateinit var favMovieObserver: Observer<DataState<PagingData<LocalMovie>>>

    @Mock
    private lateinit var favTVObserver: Observer<DataState<PagingData<LocalTV>>>

    private var dummyTV = Helper.getDummyLocalTV()
    private var dummyMovie = Helper.getDummyLocalMovie()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(coroutineRule.dispatcher)
        favViewModel =
            FavouriteViewModel(
                movieInteractors
            ).apply {
                favouriteMovies.observeForever(favMovieObserver)
                favouriteTV.observeForever(favTVObserver)
            }
    }

    @Test
    fun `get fav should be success`() {
        testGetFavMovie()
        tesGetFavTV()
    }

    @Test
    fun tesGetFavTV() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getAllFavouriteTV(favViewModel.viewModelScope))
                .thenReturn(flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyTV))
                })
            favViewModel.getFavTV()
            Mockito.verify(movieInteractors).getAllFavouriteTV(favViewModel.viewModelScope)
            Mockito.verify(favTVObserver).onChanged(DataState.Loading)
            Mockito.verify(favTVObserver).onChanged(DataState.Success(dummyTV))

        }
    }

    @Test
    fun testGetFavMovie() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getAllFavouriteMovie(favViewModel.viewModelScope))
                .thenReturn(flow {
                    emit(DataState.Loading)
                    emit(DataState.Success(dummyMovie))
                })
            favViewModel.getFavMovie()
            Mockito.verify(movieInteractors).getAllFavouriteMovie(favViewModel.viewModelScope)
            Mockito.verify(favMovieObserver).onChanged(DataState.Loading)
            Mockito.verify(favMovieObserver).onChanged(DataState.Success(dummyMovie))

        }
    }

}