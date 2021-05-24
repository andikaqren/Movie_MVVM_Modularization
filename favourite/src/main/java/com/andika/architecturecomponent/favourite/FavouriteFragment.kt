package com.andika.architecturecomponent.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andika.architecturecomponent.business.domain.utils.gone
import com.andika.architecturecomponent.business.domain.utils.visible
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.TV
import com.andika.architecturecomponent.core.ui.listener.ItemClickListener
import com.andika.architecturecomponent.core.ui.adapter.MoviePagingAdapter
import com.andika.architecturecomponent.core.ui.adapter.TVPagingAdapter
import com.andika.architecturecomponent.databinding.FragmentFavouriteBinding
import com.andika.architecturecomponent.framework.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    @Inject
    lateinit var factory:FavouriteViewModel_Factory
    private var movieAdapter = MoviePagingAdapter()
    private var tvAdapter = TVPagingAdapter()
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var binding: FragmentFavouriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        binding.rvFavoriteMovie.run {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvFavoriteTv.run {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun initObserver() {
        movieAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (itemCount > 0) {
                    binding.favoriteMovieEmpty.gone()
                } else {
                    binding.favoriteMovieEmpty.visible()
                }
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                if (itemCount > 0) {
                    binding.favoriteMovieEmpty.gone()
                } else {
                    binding.favoriteMovieEmpty.visible()
                }
            }

        })
        tvAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (itemCount > 0) {
                    binding.favoriteTvEmpty.gone()
                } else {
                    binding.favoriteTvEmpty.visible()
                }
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                if (itemCount > 0) {
                    binding.favoriteTvEmpty.gone()
                } else {
                    binding.favoriteTvEmpty.visible()
                }
            }
        })
        favouriteViewModel.favouriteMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.favoriteTvEmpty.gone()
                    binding.favoriteMovieEmpty.gone()
                }
                is DataState.Success -> {
                    movieAdapter.submitData(lifecycle, it.data)
                }
                is DataState.Error->{
                    //Temporary do nothing
                }
            }
        })

        favouriteViewModel.favouriteTV.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Loading -> {
                        binding.favoriteTvEmpty.gone()
                        binding.titleFavoritMovie.gone()
                    }
                    is DataState.Success -> {
                        tvAdapter.submitData(lifecycle, it.data)
                    }
                    is DataState.Error->{
                        //Temporary do nothing
                    }                }
            })
    }
}