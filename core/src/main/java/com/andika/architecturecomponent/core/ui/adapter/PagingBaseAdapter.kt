package com.andika.architecturecomponent.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.andika.architecturecomponent.core.R
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.andika.architecturecomponent.core.databinding.PromoBinding
import com.andika.architecturecomponent.core.ui.ItemClickListener
import com.bumptech.glide.Glide
import java.util.*

class PagingBaseAdapter :
    PagerAdapter() {
    var listener: ItemClickListener<Movie>? = null
    var list: MutableList<Movie> = ArrayList()

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    fun setData(movies: List<Movie>) {
        list.clear()
        list.addAll(movies)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val bind = PromoBinding.inflate(LayoutInflater.from(container.context), container, false)
        with(bind.root) {
            val linkPoster =
                POSTER_URL_500 + list[position].backdrop_path
            list[position].backdrop_path?.let {
                if (it.isNotEmpty()) {
                    Glide.with(container.context)
                        .load(linkPoster)
                        .placeholder(R.drawable.logomovie)
                        .into(bind.itemHomePromoIv)
                        .onLoadFailed(ContextCompat.getDrawable(context, R.drawable.logomovie))
                }
            }
            bind.starText.visibility = View.VISIBLE
            list[position].vote_average?.let {
                val rating = it * 0.5
                bind.starText.text = rating.toString()
            }
            bind.itemHomePromoName.text = list[position].title

            container.addView(bind.root)
        }
        bind.root.setOnClickListener {
            listener?.itemClick(position, list[position], it.id)
        }

        return bind.root
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as CardView)
    }

}