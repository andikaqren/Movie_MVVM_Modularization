package com.andika.architecturecomponent.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.business.data.remote.model.Movie
import com.andika.architecturecomponent.business.domain.utils.AppConstant
import com.andika.architecturecomponent.databinding.ProductBinding
import com.bumptech.glide.Glide

class MovieAdapter :
    BaseAdapter<Movie, MovieAdapter.ViewHolder>() {
    inner class ViewHolder(listener: ItemClickListener<Movie>, view: ProductBinding) :
        BaseHolder<Movie>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                val linkPoster =
                    AppConstant.POSTER_URL_500 + it.poster_path
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
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