package com.andika.architecturecomponent.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.business.data.remote.model.TV
import com.andika.architecturecomponent.business.domain.utils.AppConstant.POSTER_URL_500
import com.andika.architecturecomponent.databinding.ProductBinding
import com.bumptech.glide.Glide


class TVAdapter :
    BaseAdapter<TV, TVAdapter.ViewHolder>() {
    inner class ViewHolder(listener: ItemClickListener<TV>, view: ProductBinding) :
        BaseHolder<TV>(listener, view.root) {
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
                bind.productMoviesTitle.text = it.name
                bind.productRated.rating = rating
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVAdapter.ViewHolder {
        val itemBinding =
            ProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            listener,
            itemBinding
        )
    }

    override fun bindViewHolder(holder: ViewHolder?, data: TV?, position: Int) {
        holder?.bind()
    }

}