package com.andika.architecturecomponent.framework.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<T>(
    listener: ItemClickListener<T>,
    itemView: View
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val listener: ItemClickListener<T>
    var itemPosition = 0
        private set
    var itemData: T? = null
        private set
    var itemSelected = 0
        private set

    override fun onClick(v: View?) {
        itemSelected = itemPosition
        listener.itemClick(itemPosition, itemData, itemView.id)
    }

    fun bindData(position: Int, data: T?) {
        itemPosition = position
        itemData = data
    }

    init {
        this.listener = listener
        itemView.setOnClickListener(this)
    }
}