package com.andika.architecturecomponent.core.business.domain.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val resource = "GLOBAL"

    @JvmField
    val espressoTestIdlingResource = CountingIdlingResource(resource)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        if (!espressoTestIdlingResource.isIdleNow) {
            espressoTestIdlingResource.decrement()
        }
    }

}