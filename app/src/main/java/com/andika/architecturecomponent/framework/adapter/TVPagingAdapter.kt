package com.andika.architecturecomponent.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant
import com.andika.architecturecomponent.databinding.ProductBinding
import com.bumptech.glide.Glide

class TVPagingAdapter :
    BasePagingAdapter<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV, TVPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>() {
            override fun areItemsTheSame(oldItem: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV, newItem: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV, newItem: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(listener: ItemClickListener<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>, view: ProductBinding) :
        BaseHolder<com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV>(listener, view.root) {
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
                bind.productMoviesTitle.text = it.name
                bind.productRated.rating = rating
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVPagingAdapter.ViewHolder {
        val itemBinding =
            ProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            listener,
            itemBinding
        )
    }


    override fun bindViewHolder(holder: ViewHolder?, data: com.andika.architecturecomponent.core.business.data.remote.model.RemoteTV?, position: Int) {
        holder?.bind()
    }

}