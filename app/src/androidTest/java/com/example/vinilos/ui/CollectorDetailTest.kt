package com.example.vinilos.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinilos.R
import com.example.vinilos.util.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorDetailTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(CollectorActivity::class.java)



    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun collectorDetailsTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.fragments_rv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                ) as Matcher<in Any>?
            )
        )
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withText("Detalle Collector"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Detalle Collector")))

        val textView1 = onView(
            allOf(
                withId(R.id.collector_name), withText("Manolo Bellon"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView1.check(matches(withText("Manolo Bellon")))

        val textView2 = onView(
            allOf(
                withId(R.id.collector_email), withText("manollo@caracol.com.co"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("manollo@caracol.com.co")))

        val textView3 = onView(
            allOf(
                withId(R.id.collector_telephone), withText("3502457896"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("3502457896")))

        pressBack()

        val textView6 = onView(
            allOf(
                withText("Collector"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Collector")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Any {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }


}
