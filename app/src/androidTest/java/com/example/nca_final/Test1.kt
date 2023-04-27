package com.example.nca_final

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.nca_final.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class Test1 {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Per obrir un menu overflow:
    // openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)

    @Test
    fun test1_1() {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.action_create)).perform(click())
        onView(withId(R.id.constraintLayoutDuckCreationForm)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.recyclerViewDucks)).check(matches(isDisplayed()))
    }

    @Test
    fun test1_2() {
        val name = "New pato"
        onView(withId(R.id.action_create)).perform(click())
        onView(withId(R.id.editTextName)).perform(typeText(name))
        closeSoftKeyboard()
        onView(withId(R.id.editTextSellValue)).perform(typeText("145"))
        closeSoftKeyboard()
        onView(withId(R.id.buttonCreateDuck)).perform(click())
        onView(withText(name))
    }
}