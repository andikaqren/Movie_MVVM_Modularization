package com.andika.architecturecomponent.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.business.data.remote.model.RemoteTV
import com.andika.architecturecomponent.business.domain.utils.AppConstant
import com.andika.architecturecomponent.databinding.ProductBinding
import com.bumptech.glide.Glide

class TVPagingAdapter :
    BasePagingAdapter<RemoteTV, TVPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RemoteTV>() {
            override fun areItemsTheSame(oldItem: RemoteTV, newItem: RemoteTV): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: RemoteTV, newItem: RemoteTV): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(listener: ItemClickListener<RemoteTV>, view: ProductBinding) :
        BaseHolder<RemoteTV>(listener, view.root) {
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


    override fun bindViewHolder(holder: ViewHolder?, data: RemoteTV?, position: Int) {
        holder?.bind()
    }

}