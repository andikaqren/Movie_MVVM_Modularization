package com.andika.architecturecomponent.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.TV
import com.andika.architecturecomponent.core.business.domain.utils.gone
import com.andika.architecturecomponent.core.business.domain.utils.visible
import com.andika.architecturecomponent.core.business.interactors.MovieInteractors
import com.andika.architecturecomponent.core.di.FavouriteModule
import com.andika.architecturecomponent.core.ui.adapter.MoviePagingAdapter
import com.andika.architecturecomponent.core.ui.adapter.TVPagingAdapter
import com.andika.architecturecomponent.core.ui.listener.ItemClickListener
import com.andika.architecturecomponent.favourite.databinding.FragmentFavouriteBinding
import com.andika.architecturecomponent.favourite.di.DaggerFavouriteComponent
import com.andika.architecturecomponent.framework.presentation.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FavouriteFragment : Fragment() {

    @Inject
    lateinit var interactors: MovieInteractors

    private var movieAdapter = MoviePagingAdapter()
    private var tvAdapter = TVPagingAdapter()
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerFavouriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavouriteModule::class.java
                )
            )
            .build()
            .inject(this)
        favouriteViewModel.setApi(interactors)
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        favouriteViewModel.reloadData()
    }

    private fun initView() {
        tvAdapter.listener = object : ItemClickListener<TV> {
            override fun itemClick(position: Int, item: TV?, view: Int) {
                DetailActivity.start(context!!, TV, item!!)
            }

        }
        movieAdapter.listener = object : ItemClickListener<Movie> {
            override fun itemClick(position: Int, item: Movie?, view: Int) {
                DetailActivity.start(context!!, MOVIE, item!!)
            }

        }
        binding.rvFavouriteMovie.run {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvFavouriteTv.run {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun initObserver() {
        movieAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {

            override fun onStateRestorationPolicyChanged() {
                super.onStateRestorationPolicyChanged()
                if (movieAdapter.itemCount > 0) {
                    binding.favouriteMovieEmpty.gone()
                } else {
                    binding.favouriteMovieEmpty.visible()
                }
            }

        })
        tvAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {

            override fun onStateRestorationPolicyChanged() {
                super.onStateRestorationPolicyChanged()
                if (tvAdapter.itemCount > 0) {
                    binding.favouriteTvEmpty.gone()
                } else {
                    binding.favouriteTvEmpty.visible()
                }
            }
        })
        favouriteViewModel.favouriteMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.favouriteMovieEmpty.gone()
                }
                is DataState.Success -> {
                    movieAdapter.submitData(lifecycle, it.data)
                }
                is DataState.Error -> {
                    binding.favouriteMovieEmpty.visible()
                }
            }
        })

        favouriteViewModel.favouriteTV.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Loading -> {
                        binding.favouriteTvEmpty.gone()
                    }
                    is DataState.Success -> {
                        tvAdapter.submitData(lifecycle, it.data)
                    }
                    is DataState.Error -> {
                        binding.favouriteTvEmpty.visible()
                    }
                }
            })
    }
}