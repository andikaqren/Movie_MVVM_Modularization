package com.andika.architecturecomponent.favourite.di

import android.content.Context
import com.andika.architecturecomponent.core.di.FavouriteModule
import com.andika.architecturecomponent.favourite.FavouriteFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Component(dependencies = [FavouriteModule::class])
interface FavouriteComponent {

    @ExperimentalCoroutinesApi
    fun inject(fragment: FavouriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favModule: FavouriteModule): Builder
        fun build(): FavouriteComponent
    }
}