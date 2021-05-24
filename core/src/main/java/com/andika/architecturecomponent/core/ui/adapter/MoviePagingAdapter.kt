package com.andika.architecturecomponent.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.andika.architecturecomponent.core.R
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.andika.architecturecomponent.core.databinding.ProductBinding
import com.andika.architecturecomponent.core.ui.listener.ItemClickListener
import com.bumptech.glide.Glide

class MoviePagingAdapter :
    BasePagingAdapter<Movie, MoviePagingAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }


    inner class ViewHolder(listener: ItemClickListener<Movie>, view: ProductBinding) :
        BaseHolder<Movie>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                val linkPoster =
                    POSTER_URL_500 + it.poster_path
                val rating: Float = it.vote_average!!.toFloat() * 0.5f
                Glide.with(context)
                    .load(linkPoster)
                    .placeholder(R.drawable.logomovie)
                    .into(bind.moviesMiniPoster)
                bind.productMoviesTitle.text = it.title
                bind.productRated.rating = rating
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding =
            ProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            listener,
            itemBinding
        )
    }


    override fun bindViewHolder(holder: ViewHolder?, data: Movie?, position: Int) {
        holder?.bind()
    }


}