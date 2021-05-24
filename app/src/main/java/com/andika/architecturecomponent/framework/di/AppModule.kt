package com.andika.architecturecomponent.framework.presentation

import com.andika.architecturecomponent.framework.presentation.detail.DetailViewModel
import com.andika.architecturecomponent.framework.presentation.home.movie.MovieViewModel
import com.andika.architecturecomponent.framework.presentation.home.tv.TvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
}