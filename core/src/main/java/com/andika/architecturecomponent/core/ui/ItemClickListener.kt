package com.andika.architecturecomponent.core.ui

import androidx.annotation.IdRes

interface ItemClickListener<T> {
    fun itemClick(position: Int, item: T?, @IdRes view: Int)
}