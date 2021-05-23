package com.andika.architecturecomponent.framework.adapter

import androidx.annotation.IdRes

interface ItemClickListener<T> {
    fun itemClick(position: Int, item: T?, @IdRes view: Int)
}