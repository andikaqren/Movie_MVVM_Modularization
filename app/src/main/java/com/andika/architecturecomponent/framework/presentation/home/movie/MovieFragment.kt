package com.andika.architecturecomponent.framework.presentation.home.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.business.domain.utils.gone
import com.andika.architecturecomponent.business.domain.utils.visible
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.databinding.FragmentMoviesBinding
import com.andika.architecturecomponent.core.ui.ItemClickListener
import com.andika.architecturecomponent.core.ui.adapter.MoviePagingAdapter
import com.andika.architecturecomponent.core.ui.adapter.PagingBaseAdapter
import com.andika.architecturecomponent.framework.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment(), ItemClickListener<Movie> {
    private lateinit var binding: FragmentMoviesBinding
    private var nowPlayingAdapter = MoviePagingAdapter()
    private var popularAdapter = MoviePagingAdapter()
    private var upcomingAdapter = MoviePagingAdapter()
    private var topRatedAdapter = PagingBaseAdapter()
    private val viewModel: MovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadData()
    }

    fun initView() {
        binding.container.rvUpcoming.run {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.container.rvNowPlaying.run {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.container.rvPopular.run {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        popularAdapter.listener = this
        upcomingAdapter.listener = this
        nowPlayingAdapter.listener = this
        topRatedAdapter.listener = this
        binding.homeVp.adapter = topRatedAdapter
        binding.introIndicator.setViewPager(binding.homeVp)


    }

    fun initObserver() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.container.noDataNowPlaying.cvProduct.gone()
                    binding.container.rvNowPlaying.gone()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.visible()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.startShimmerAnimation()
                }
                is DataState.Error -> {
                    binding.container.noDataNowPlaying.cvProduct.visible()
                    binding.container.rvNowPlaying.gone()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.gone()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.stopShimmerAnimation()
                }
                is DataState.Success -> {
                    nowPlayingAdapter.submitData(lifecycle, it.data)
                    binding.container.noDataNowPlaying.cvProduct.gone()
                    binding.container.rvNowPlaying.visible()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.gone()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.stopShimmerAnimation()
                }
            }
        })
        viewModel.popularMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.container.noDataPopular.cvProduct.gone()
                    binding.container.rvPopular.gone()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.visible()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.startShimmerAnimation()
                }
                is DataState.Error -> {
                    binding.container.noDataPopular.cvProduct.visible()
                    binding.container.rvPopular.gone()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.gone()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.stopShimmerAnimation()
                }
                is DataState.Success -> {
                    popularAdapter.submitData(lifecycle, it.data)
                    binding.container.noDataPopular.cvProduct.gone()
                    binding.container.rvPopular.visible()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.gone()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.stopShimmerAnimation()
                }
            }
        })

        viewModel.upcomingMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.container.noDataUpcoming.cvProduct.gone()
                    binding.container.rvUpcoming.gone()
                    binding.container.layoutShimmerHomeUpcoming.shimmerLayout.visible()
                    binding.container.layoutShimmerHomeUpcoming.shimmerLayout.startShimmerAnimation()
                }
                is DataState.Error -> {
                    binding.container.noDataUpcoming.cvProduct.visible()
                    binding.container.rvUpcoming.gone()
                    binding.container.layoutShimmerHomeUpcoming.shimmerLayout.gone()
                    binding.container.layoutShimmerHomeUpcoming.shimmerLayout.stopShimmerAnimation()
                }
                is DataState.Success -> {
                    upcomingAdapter.submitData(lifecycle, it.data)
                    binding.container.noDataUpcoming.cvProduct.gone()
                    binding.container.rvUpcoming.visible()
                    binding.container.layoutShimmerHomeUpcoming.shimmerLayout.gone()
                    binding.container.layoutShimmerHomeUpcoming.shimmerLayout.stopShimmerAnimation()
                }
            }
        })
        viewModel.topMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.layoutShimmerHomeVp.shimmerLayout.visible()
                    binding.layoutShimmerHomeVp.shimmerLayout.startShimmerAnimation()
                    binding.homeVp.gone()
                }
                is DataState.Error -> {
                    binding.layoutShimmerHomeVp.shimmerLayout.visible()
                    binding.layoutShimmerHomeVp.shimmerLayout.stopShimmerAnimation()
                    binding.homeVp.gone()
                }
                is DataState.Success -> {
                    topRatedAdapter.setData(it.data.results)
                    binding.layoutShimmerHomeVp.shimmerLayout.gone()
                    binding.layoutShimmerHomeVp.shimmerLayout.stopShimmerAnimation()
                    binding.homeVp.visible()
                }
            }
        })
    }

    override fun itemClick(position: Int, item: Movie?, view: Int) {
        item?.let {
            DetailActivity.start(requireContext(), MOVIE, it)
        }

    }
}