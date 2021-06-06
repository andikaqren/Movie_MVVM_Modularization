package com.andika.architecturecomponent.framework.presentation.home.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.business.domain.utils.gone
import com.andika.architecturecomponent.business.domain.utils.visible
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant
import com.andika.architecturecomponent.core.ui.adapter.TVPagingAdapter
import com.andika.architecturecomponent.core.ui.adapter.TvPagerAdapter
import com.andika.architecturecomponent.core.ui.listener.ItemClickListener
import com.andika.architecturecomponent.databinding.FragmentTvBinding
import com.andika.architecturecomponent.framework.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvFragment : Fragment(), ItemClickListener<TV> {
    private lateinit var binding: FragmentTvBinding
    private lateinit var latestJob: Job
    private lateinit var popularJob: Job
    private lateinit var topJob: Job
    private var latestAdapter = TVPagingAdapter()
    private var popularAdapter = TVPagingAdapter()
    private var topRatedAdapter = TvPagerAdapter()
    private val viewModel: TvViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvBinding.inflate(layoutInflater)
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

    private fun initView() {
        binding.container.rvUpcoming.gone()
        binding.container.tvUpcoming.gone()
        binding.container.noDataUpcoming.cvProduct.gone()
        binding.container.layoutShimmerHomeUpcoming.shimmerLayout.gone()
        binding.container.rvPopular.run {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.container.rvNowPlaying.run {
            adapter = latestAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        topRatedAdapter.listener = this
        popularAdapter.listener = this
        latestAdapter.listener = this
        binding.homeVp.adapter = topRatedAdapter


    }

    private fun initObserver() {
        viewModel.latestTV.observe(viewLifecycleOwner, {
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
                    latestJob = lifecycleScope.launch {
                        latestAdapter.submitData(it.data)
                    }
                    binding.container.noDataNowPlaying.cvProduct.gone()
                    binding.container.rvNowPlaying.visible()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.gone()
                    binding.container.layoutShimmerHomeNowPlaying.shimmerLayout.stopShimmerAnimation()
                }
            }
        })
        viewModel.popularTV.observe(viewLifecycleOwner, {
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
                    popularJob = lifecycleScope.launch {
                        popularAdapter.submitData(it.data)
                    }
                    binding.container.noDataPopular.cvProduct.gone()
                    binding.container.rvPopular.visible()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.gone()
                    binding.container.layoutShimmerHomePopular.shimmerLayout.stopShimmerAnimation()
                }
            }
        })
        viewModel.topTV.observe(viewLifecycleOwner, {
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
                    topJob = lifecycleScope.launch {
                        topRatedAdapter.setData(it.data.results)
                    }
                    binding.layoutShimmerHomeVp.shimmerLayout.gone()
                    binding.layoutShimmerHomeVp.shimmerLayout.startShimmerAnimation()
                    binding.homeVp.visible()
                }
            }
        })

    }


    override fun itemClick(position: Int, item: TV?, view: Int) {
        item?.let {
            DetailActivity.start(requireContext(), AppConstant.TV, it)
        }
    }
}