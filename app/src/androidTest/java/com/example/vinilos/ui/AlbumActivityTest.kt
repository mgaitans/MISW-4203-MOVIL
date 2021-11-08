package com.example.vinilos.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinilos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(
        AlbumActivity::class.java
    )

    /*##HU01-Manú-Álbumes Y Vista Álbumes*/
    @Test
    @Throws(Exception::class)
    fun click_opensLisAlbums() {
        onView(withId(R.id.action_switch_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.action_switch_layout)).perform(click())
        onView(withId(R.id.action_switch_layout)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun click_opensInicio() {
        onView(withId(R.id.action_inicio)).check(matches(isDisplayed()))
        onView(withId(R.id.action_inicio)).perform(click())
        onView(withId(R.id.action_inicio)).check(matches(isDisplayed()))
    }


    /*##HU02-Detalle de Álbum*/


}
