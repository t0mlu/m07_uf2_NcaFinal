package com.example.nca_final


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.nca_final.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class Test4 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test4() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_create), withContentDescription("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.editTextName),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayoutDuckCreationForm),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_content_main),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("New pato"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.editTextSellValue),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayoutDuckCreationForm),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_content_main),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("204"), closeSoftKeyboard())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.spinnerRarity),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayoutDuckCreationForm),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_content_main),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())

        // He canviat aquest codi manualment perqué el codi original tenia un error amb el spinner
        // Source: https://stackoverflow.com/questions/70796792
        val appCompatCheckedTextView = onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(3)
        appCompatCheckedTextView.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.buttonCreateDuck), withText("Create"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayoutDuckCreationForm),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_content_main),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.name), withText("New pato"),
                withParent(withParent(withId(R.id.duckLayout))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("New pato")))
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
