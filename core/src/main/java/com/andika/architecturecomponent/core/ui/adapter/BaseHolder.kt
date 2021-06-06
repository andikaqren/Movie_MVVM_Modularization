package com.andika.architecturecomponent.core.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andika.architecturecomponent.core.ui.listener.ItemClickListener

abstract class BaseHolder<T>(
    private val listener: ItemClickListener<T>,
    itemView: View
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var itemPosition = 0
    var itemData: T? = null
    private var itemSelected = 0


    override fun onClick(v: View?) {
        itemSelected = itemPosition
        listener.itemClick(itemPosition, itemData, itemView.id)
    }

    fun bindData(position: Int, data: T?) {
        itemPosition = position
        itemData = data
        itemView.setOnClickListener(this)
    }

}