package com.example.nca_final

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.nca_final.adapters.DuckListAdapter
import com.example.nca_final.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class Test3 {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test3_1() {
        val name = "Pato ${Date().time}"
        onView(withId(R.id.action_create)).perform(click())
        onView(withId(R.id.editTextName)).perform(typeText(name))
        closeSoftKeyboard()
        onView(withId(R.id.editTextSellValue)).perform(typeText("145"))
        closeSoftKeyboard()
        onView(withId(R.id.buttonCreateDuck)).perform(click())
        onView(withId(R.id.recyclerViewDucks)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DuckListAdapter.DuckViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.textViewUrl)).perform(click())
        // TODO: D'alguna forma checkear que l'intent s'ha executat correctament?
    }
}