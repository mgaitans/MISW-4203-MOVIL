package com.example.vinilos.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinilos.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import kotlin.random.Random

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun createAlbumTest() {
        val frameLayout = onView(
            allOf(
                withId(R.id.action_bar), withContentDescription("Albumes"),
                withParent(withParent(withId(R.id.action_album))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val albumName = "album de prueba " + Random.nextInt(0,7000000).toString()
        val textView = onView(
            allOf(
                withId(R.id.new_album), withContentDescription("Nuevo Album"),
                withParent(withParent(withId(R.id.action_bar))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.new_album), withContentDescription("Nuevo Album"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textView2 = onView(
            allOf(
                withText("Nuevo Album"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Nuevo Album")))

        val appCompatEditText1 = onView(
            allOf(
                withId(R.id.album_name), withText(albumName),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText1.perform(pressImeActionButton())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.album_cover),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.album_cover),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), longClick())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.album_cover),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText4.perform(
            scrollTo(),
            replaceText("https://www.todorock.com/wp-content/uploads/2019/05/iron-maiden-1992.jpg"),
            closeSoftKeyboard()
        )


        val textView5 = onView(
            allOf(
                withText("Fecha Lanzamiento"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Fecha Lanzamiento")))

        val textView6 = onView(
            allOf(
                withText("Sello Discográfico"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Sello Discográfico")))

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.album_record),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    7
                )
            )
        )
        appCompatSpinner.perform(scrollTo(), click())

        val seleccionarsello = onView(
            allOf(
                withId(android.R.id.text1), withText("Sony Music"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        seleccionarsello.perform(click())



        val appCompatSpinner2 = onView(
            allOf(
                withId(R.id.album_genre),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    9
                )
            )
        )
        appCompatSpinner2.perform(scrollTo(), click())

        val checkedTextView2 = onView(
            allOf(
                withId(android.R.id.text1), withText("Salsa"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView2.check(matches(isDisplayed()))

        val seleccionargenero = onView(
            allOf(
                withId(android.R.id.text1), withText("Salsa"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView2.check(matches(isDisplayed()))
        seleccionargenero.perform(click())

        onView(withId(R.id.nav_host_fragment))
            .perform(swipeUp())



        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.album_description),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    11
                )
            )
        )

        appCompatEditText7.perform(scrollTo(), replaceText("hola mundo"), closeSoftKeyboard())

        val button = onView(
            allOf(
                withId(R.id.create_album), withText("AGREGAR"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val materialButton = onView(
            allOf(
                withId(R.id.create_album), withText("Agregar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    12
                )
            )
        )
        onView(withId(R.id.nav_host_fragment))
            .perform(swipeUp())
        button.perform(scrollTo(),click())

        Thread.sleep(3000)

        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.fragments_rv)
        val itemCount = recyclerView.adapter!!.itemCount

        onView(withId(R.id.fragments_rv))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))

        onView(withId(R.id.nav_host_fragment))
            .perform(swipeUp())

        val textView9 = onView(
            allOf(
                withId(R.id.album_name),
                withText(albumName),
                withContentDescription("nombre del album: $albumName"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
            )
        )
        textView9.perform(scrollTo())
        textView9.check(matches(withText(albumName)))


    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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