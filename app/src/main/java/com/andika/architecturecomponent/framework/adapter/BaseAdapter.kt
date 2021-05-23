package com.andika.architecturecomponent.framework.adapter

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<S, T : BaseHolder<S>> : RecyclerView.Adapter<T>() {
    lateinit var listener: ItemClickListener<S>
    var list: MutableList<S> = ArrayList()

    override fun onBindViewHolder(holder: T, position: Int) {
        val data = getItem(position)
        holder.bindData(position, data)
        bindViewHolder(holder, data, position)
    }

    private fun add(data: S?) {
        data?.let {
            list.add(it)
            notifyItemInserted(list.size)
        }
    }

    private fun replace(data: S?, index: Int) {
        data?.let {
            list[index] = it
        }
    }

    private fun addAll(list: MutableList<S>?) {
        list?.let {
            for (item in it) {
                add(item)
            }
        }

    }

    private fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): S? {
        return if (position < list.size) list[position] else null
    }

    override fun getItemCount(): Int {
        return list.size
    }

    protected abstract fun bindViewHolder(holder: T?, data: S?, position: Int)
}