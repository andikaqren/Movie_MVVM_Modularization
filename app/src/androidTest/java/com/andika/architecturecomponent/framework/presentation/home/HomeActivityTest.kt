package com.andika.architecturecomponent.framework.presentation.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.andika.architecturecomponent.R
import com.andika.architecturecomponent.business.domain.utils.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeActivityTest {

    val testSize = 5

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.home_vp))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.home_vp))
            .perform(ViewPagerActions.scrollToLast())
        onView(withId(R.id.home_vp))
            .perform(ViewPagerActions.scrollToFirst())
        onView(withId(R.id.rv_now_playing))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_now_playing))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_now_playing))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testSize))
        onView(withId(R.id.ll_home_popular)).perform(scrollTo())
        onView(withId(R.id.rv_popular))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_popular))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_popular))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testSize))
        onView(withId(R.id.ll_home_upcoming)).perform(scrollTo())
        onView(withId(R.id.rv_upcoming))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_upcoming))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_upcoming))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testSize))
        onView(withId(R.id.ll_home_now_playing)).perform(scrollTo())
        onView(withId(R.id.home_vp)).perform(click())
        loadDetailMovies()

    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.iv_detail_frame_fav)).perform(click())
        onView(withId(R.id.iv_detail_frame_fav)).perform(click())
        onView(withId(R.id.iv_detail_frame_fav)).perform(click())
        onView(withId(R.id.detail_summary_text)).perform(scrollTo())
        onView(withId(R.id.detail_recomendation_title)).perform(scrollTo())
        pressBack()
        loadTV()
    }

    @Test
    fun loadTV() {
        onView(withId(R.id.tvFragment)).perform(click())
        onView(withId(R.id.home_vp))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.home_vp))
            .perform(ViewPagerActions.scrollToLast())
        onView(withId(R.id.home_vp))
            .perform(ViewPagerActions.scrollToFirst())
        onView(withId(R.id.rv_now_playing))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_now_playing))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_now_playing))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testSize))
        onView(withId(R.id.ll_home_popular)).perform(scrollTo())
        onView(withId(R.id.rv_popular))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_popular))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.rv_popular))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testSize))
        onView(withId(R.id.ll_home_now_playing)).perform(scrollTo())
        onView(withId(R.id.home_vp)).perform(click())
        loadDetailTV()
    }

    fun loadDetailTV() {
        onView(withId(R.id.iv_detail_frame_fav)).perform(click())
        onView(withId(R.id.iv_detail_frame_fav)).perform(click())
        onView(withId(R.id.iv_detail_frame_fav)).perform(click())
        onView(withId(R.id.detail_title)).perform(scrollTo())
        onView(withId(R.id.detail_recomendation_title)).perform(scrollTo())
        pressBack()
        loadFavourite()
    }

    fun loadFavourite() {
        onView(withId(R.id.favoritFragment)).perform(click())
        onView(withId(R.id.rv_favorite_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_favorite_tv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_favorite_movie)).perform(click())
        pressBack()
        onView(withId(R.id.rv_favorite_tv)).perform(click())
    }


}