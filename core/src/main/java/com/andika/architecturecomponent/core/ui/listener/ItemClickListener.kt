package com.andika.architecturecomponent.core.ui.listener

import androidx.annotation.IdRes

interface ItemClickListener<T> {
    fun itemClick(position: Int, item: T?, @IdRes view: Int)
}