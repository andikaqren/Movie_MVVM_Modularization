package com.andika.architecturecomponent.framework.presentation.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.core.business.domain.state.DataState
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.EMPTY
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.ID
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.MOVIE
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.TV
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.TYPE
import com.andika.architecturecomponent.business.domain.utils.gone
import com.andika.architecturecomponent.business.domain.utils.visible
import com.andika.architecturecomponent.databinding.ActivityDetailBinding
import com.andika.architecturecomponent.framework.adapter.ItemClickListener
import com.andika.architecturecomponent.framework.adapter.MovieAdapter
import com.andika.architecturecomponent.framework.adapter.TVAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private var recomendationTVAdapter = TVAdapter()
    private var recomendationAdapter = MovieAdapter()
    private var similarTVAdapter = TVAdapter()
    private var similarAdapter = MovieAdapter()
    private var detailJob: Job? = null

    companion object {
        var type = MOVIE
        var isFavourite = false
        var id = EMPTY
        lateinit var movie: com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
        lateinit var tv: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
        fun start(context: Context, type: String, data: Any) {
            val intent = Intent(context, DetailActivity::class.java)
            when (type) {
                MOVIE -> {
                    movie = data as com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
                    id = movie.id.toString()
                }
                TV -> {
                    tv = data as com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
                    id = tv.id.toString()
                }
            }
            this.type = type
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDeepLink()
    }

    fun initDeepLink() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                val deepLink: Uri?
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    type = deepLink?.getQueryParameter(TYPE) ?: ""
                    id = deepLink?.getQueryParameter(ID) ?: ""
                    detailJob?.cancel()
                    detailJob = lifecycleScope.launch(Dispatchers.IO) {
                        when (type) {
                            MOVIE -> detailViewModel.getMoviesDetail(id)
                            TV -> detailViewModel.getTvDetail(id)
                        }
                    }
                } else {
                    initView()
                    observeView()
                }

            }
            .addOnFailureListener(this) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }

        detailViewModel.movie.observe(this, {
            when (it) {
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> showLoading()
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {

                }
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                    movie = it.data
                    initView()
                    observeView()
                }
            }
        })
        detailViewModel.tv.observe(this, {
            when (it) {
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> showLoading()
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {

                }
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                    tv = it.data
                    initView()
                    observeView()
                }
            }
        })
    }

    private fun showLoading() {

    }

    private fun observeView() {
        when (type) {
            MOVIE -> {
                detailViewModel.favMovie.observe(this, {
                    when (it) {
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> binding.detailFrame.ivDetailFrameFav.gone()
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {
                            isFavourite = false
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                            isFavourite = true
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                    }
                })
                detailViewModel.recomendationMovies.observe(this, {
                    when (it) {
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> {
                            binding.itemDetail.detailRecomendationTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.visible()
                            binding.itemDetail.detailShimmer.startShimmerAnimation()
                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                            binding.itemDetail.detailRecomendationTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()
                            binding.itemDetail.detailSimiliarRv.visible()
                            recomendationAdapter.list = it.data.results.toMutableList()
                            recomendationAdapter.notifyDataSetChanged()
                            if (recomendationAdapter.list.isNullOrEmpty()) binding.itemDetail.detailRecomendationTextCommingSoon.visible()

                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {
                            binding.itemDetail.detailRecomendationTextCommingSoon.visible()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()


                        }
                    }
                })
                detailViewModel.similarMovies.observe(this, {
                    when (it) {
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> {
                            binding.itemDetail.detailSimiliarTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.visible()
                            binding.itemDetail.detailShimmer.startShimmerAnimation()
                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                            binding.itemDetail.detailSimiliarTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()
                            binding.itemDetail.detailRecomendationRv.visible()
                            similarAdapter.list = it.data.results.toMutableList()
                            similarAdapter.notifyDataSetChanged()
                            if (similarAdapter.list.isNullOrEmpty()) binding.itemDetail.detailSimiliarTextCommingSoon.visible()

                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {
                            binding.itemDetail.detailSimiliarTextCommingSoon.visible()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()
                        }
                    }
                })
            }
            TV -> {
                detailViewModel.favTV.observe(this, {
                    when (it) {
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> binding.detailFrame.ivDetailFrameFav.gone()
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {
                            isFavourite = false
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                            isFavourite = true
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                    }
                })
                detailViewModel.recomendationTV.observe(this, {
                    when (it) {
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> {
                            binding.itemDetail.detailRecomendationTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.visible()
                            binding.itemDetail.detailShimmer.startShimmerAnimation()
                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                            binding.itemDetail.detailRecomendationTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()
                            recomendationTVAdapter.list = it.data.results.toMutableList()
                            recomendationTVAdapter.notifyDataSetChanged()

                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {
                            binding.itemDetail.detailRecomendationTextCommingSoon.visible()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()

                        }
                    }
                })
                detailViewModel.similarTV.observe(this, {
                    when (it) {
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Loading -> {
                            binding.itemDetail.detailSimiliarTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.visible()
                            binding.itemDetail.detailShimmer.startShimmerAnimation()
                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                            binding.itemDetail.detailSimiliarTextCommingSoon.gone()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()
                            similarTVAdapter.list = it.data.results.toMutableList()
                            similarTVAdapter.notifyDataSetChanged()

                        }
                        is com.andika.architecturecomponent.core.business.domain.state.DataState.Error -> {
                            binding.itemDetail.detailSimiliarTextCommingSoon.visible()
                            binding.itemDetail.detailShimmer.gone()
                            binding.itemDetail.detailShimmer.stopShimmerAnimation()
                        }
                    }
                })
            }
        }
        detailViewModel.dynamicLink.observe(this, {
            when (it) {
                is com.andika.architecturecomponent.core.business.domain.state.DataState.Success -> {
                    showBottomSheet(it.data)
                }
            }
        })
    }


    private fun setFav() {
        if (isFavourite) {
            binding.detailFrame.ivDetailFrameFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_baseline_favourite_red
                )
            )

        } else {
            binding.detailFrame.ivDetailFrameFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_baseline_favourite
                )
            )
        }
    }


    private fun showSnackBar() {
        if (isFavourite) {
            Snackbar.make(
                binding.detailFrame.ivDetailFrameFav,
                "Success add to favorite",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                binding.detailFrame.ivDetailFrameFav,
                "Success remove from favorite",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }


    private fun initView() {
        binding.itemDetail.fabShare.setOnClickListener {
            getDynamicLink()
        }
        binding.detailFrame.ivDetailFrameFav.setOnClickListener {
            isFavourite = !isFavourite
            when (type) {
                MOVIE -> detailViewModel.setFav(isFavourite, type, movie)
                TV -> detailViewModel.setFav(isFavourite, type, tv)
            }
            setFav()
            showSnackBar()
        }
        when (type) {
            MOVIE -> {
                detailViewModel.reloadData(filename = movie.id)
                val linkPoster =
                    POSTER_URL_500 + movie.poster_path
                val linkPoster2 =
                    POSTER_URL_500 + movie.backdrop_path
                movie.vote_average?.let {
                    val rating = it * 0.5f
                    binding.detailFrame.rbDetailFrame.rating = rating.toFloat()
                }
                binding.detailFrame.tvDetailFrameTitle.text = movie.title
                binding.itemDetail.detailReleaseDate.text = movie.release_date
                binding.itemDetail.detailSummaryText.text = movie.overview
                binding.itemDetail.detailTitle.text = movie.original_title
                Glide.with(this)
                    .load(linkPoster)
                    .into(binding.detailFrame.ivDetailFramePoster)
                Glide.with(this)
                    .load(linkPoster2)
                    .into(binding.detailFrame.ivDetailFrameCover)
                binding.itemDetail.detailRecomendationRv.run {
                    adapter = recomendationAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                }
                binding.itemDetail.detailSimiliarRv.run {
                    adapter = similarAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                }
                similarAdapter.listener = object : ItemClickListener<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie> {
                    override fun itemClick(position: Int, item: com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie?, view: Int) {
                        start(this@DetailActivity, type, item!!)
                    }

                }
                recomendationAdapter.listener = object : ItemClickListener<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie> {
                    override fun itemClick(position: Int, item: com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie?, view: Int) {
                        start(this@DetailActivity, type, item!!)
                    }
                }
            }
            TV -> {
                detailViewModel.reloadDataTV(filename = tv.id)
                val linkPoster =
                    POSTER_URL_500 + tv.poster_path
                val linkPoster2 =
                    POSTER_URL_500 + tv.backdrop_path
                binding.detailFrame.tvDetailFrameTitle.text = tv.name
                tv.vote_average?.let {
                    val rating = it * 0.5f
                    binding.detailFrame.rbDetailFrame.rating = rating.toFloat()
                }
                binding.itemDetail.detailReleaseDate.text = tv.first_air_date
                binding.itemDetail.detailSummaryText.text = tv.overview
                binding.itemDetail.detailTitle.text = tv.original_name
                Glide.with(this)
                    .load(linkPoster)
                    .into(binding.detailFrame.ivDetailFramePoster)
                Glide.with(this)
                    .load(linkPoster2)
                    .into(binding.detailFrame.ivDetailFrameCover)
                binding.itemDetail.detailRecomendationRv.run {
                    adapter = recomendationTVAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
                binding.itemDetail.detailSimiliarRv.run {
                    adapter = similarTVAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
                similarTVAdapter.listener = object : ItemClickListener<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV> {
                    override fun itemClick(position: Int, item: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV?, view: Int) {
                        start(this@DetailActivity, type, item!!)
                    }

                }
                recomendationTVAdapter.listener = object : ItemClickListener<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV> {
                    override fun itemClick(position: Int, item: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV?, view: Int) {
                        start(this@DetailActivity, type, item!!)
                    }
                }
            }
        }
    }

    private fun getDynamicLink() {
        when (type) {
            MOVIE -> intent.putExtra(MOVIE, movie)
            TV -> intent.putExtra(TV, tv)
        }
        val deeplink = "${com.andika.architecturecomponent.core.business.domain.utils.AppConstant.DEEPLINKURL}?$TYPE=$type&$ID=$id"
        detailViewModel.getDynamicLink(deeplink)
    }

    private fun showBottomSheet(data: String) {
        val dialog = DetailBottomSheetShare()
        dialog.link = data
        dialog.show(supportFragmentManager, dialog.tag)
    }


}