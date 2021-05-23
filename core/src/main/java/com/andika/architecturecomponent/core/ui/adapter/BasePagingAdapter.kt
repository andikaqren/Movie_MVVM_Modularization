package com.andika.architecturecomponent.core.ui.adapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.andika.architecturecomponent.core.ui.ItemClickListener

abstract class BasePagingAdapter<S : Any, T : BaseHolder<S>>(diffUtil: DiffUtil.ItemCallback<S>) :
    PagingDataAdapter<S, T>(diffUtil) {
    lateinit var listener: ItemClickListener<S>

    override fun onBindViewHolder(holder: T, position: Int) {
        val data = getItem(position)
        holder.bindData(position, data)
        bindViewHolder(holder, data, position)
    }


    protected abstract fun bindViewHolder(holder: T?, data: S?, position: Int)
}