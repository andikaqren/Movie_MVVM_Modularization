package com.andika.architecturecomponent.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant
import com.andika.architecturecomponent.databinding.ProductBinding
import com.bumptech.glide.Glide

class MovieAdapter :
    BaseAdapter<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie, MovieAdapter.ViewHolder>() {
    inner class ViewHolder(listener: ItemClickListener<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>, view: ProductBinding) :
        BaseHolder<com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                val linkPoster =
                    com.andika.architecturecomponent.core.business.domain.utils.AppConstant.POSTER_URL_500 + it.poster_path
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

    override fun bindViewHolder(holder: ViewHolder?, data: com.andika.architecturecomponent.core.business.data.remote.model.RemoteMovie?, position: Int) {
        holder?.bind()
    }

}