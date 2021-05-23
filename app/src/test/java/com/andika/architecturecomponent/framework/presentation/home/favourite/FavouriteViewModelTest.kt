package com.andika.architecturecomponent.framework.presentation.home.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
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
    private lateinit var favViewModel: com.andika.architecturecomponent.favourite.FavouriteViewModel

    @Mock
    private lateinit var movieInteractors: com.andika.architecturecomponent.core.business.interactors.MovieInteractors

    @Mock
    private lateinit var favMovieObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.local.model.LocalMovie>>>

    @Mock
    private lateinit var favTVObserver: Observer<com.andika.architecturecomponent.core.business.domain.state.DataState<PagingData<com.andika.architecturecomponent.core.business.data.local.model.LocalTV>>>

    private var dummyTV = com.andika.architecturecomponent.core.business.domain.utils.Helper.getDummyLocalTV()
    private var dummyMovie = com.andika.architecturecomponent.core.business.domain.utils.Helper.getDummyLocalMovie()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(coroutineRule.dispatcher)
        favViewModel =
            com.andika.architecturecomponent.favourite.FavouriteViewModel(
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
                    emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                    emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyTV))
                })
            favViewModel.getFavTV()
            Mockito.verify(movieInteractors).getAllFavouriteTV(favViewModel.viewModelScope)
            Mockito.verify(favTVObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            Mockito.verify(favTVObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyTV))

        }
    }

    @Test
    fun testGetFavMovie() {
        coroutineRule.runBlockingTest {
            Mockito.`when`(movieInteractors.getAllFavouriteMovie(favViewModel.viewModelScope))
                .thenReturn(flow {
                    emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
                    emit(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyMovie))
                })
            favViewModel.getFavMovie()
            Mockito.verify(movieInteractors).getAllFavouriteMovie(favViewModel.viewModelScope)
            Mockito.verify(favMovieObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Loading)
            Mockito.verify(favMovieObserver).onChanged(com.andika.architecturecomponent.core.business.domain.state.DataState.Success(dummyMovie))

        }
    }

}