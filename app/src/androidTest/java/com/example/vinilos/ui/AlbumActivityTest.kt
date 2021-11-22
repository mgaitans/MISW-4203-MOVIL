package com.example.vinilos.ui

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinilos.R
import com.example.vinilos.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(AlbumActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /*##HU01-Manú-Álbumes Y Vista Álbumes*/
    @Throws(Exception::class)
    fun click_opensLisAlbums() {
        onView(withId(R.id.action_album)).check(matches(isDisplayed()))
        onView(withId(R.id.action_album)).perform(click())
        onView(withId(R.id.action_album)).check(matches(isDisplayed()))
    }

    @Test
    fun albumListTest() {
        val textView = onView(
            allOf(
                withText("Albumes"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Albumes")))

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.nav_host_fragment),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.textView6), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Buscando América")))

        val imageView = onView(
            allOf(
                withId(R.id.navigation_bar_item_icon_view),
                withParent(
                    allOf(
                        withId(R.id.dice_image), withContentDescription("Albumes"),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
    }


}
