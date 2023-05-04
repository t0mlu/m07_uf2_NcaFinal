package com.example.nca_final

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.PositionAssertions.*
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
class Test2 {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Aquest test falla després de la modificació demanada al punt 5 de l'enunciat
    @Test
    fun test2_1() {
        onView(withId(R.id.action_create)).perform(click())
        onView(withId(R.id.editTextName)).check(isCompletelyAbove(withId(R.id.editTextSellValue)))
        onView(withId(R.id.editTextSellValue)).check(isCompletelyAbove(withId(R.id.spinnerRarity)))
        onView(withId(R.id.buttonCreateDuck)).check(isCompletelyBelow(withId(R.id.spinnerRarity)))
    }

    // Aquest és el nou test que funcional
    @Test
    fun test2_1_1() {
        onView(withId(R.id.action_create)).perform(click())
        onView(withId(R.id.editTextName)).check(isCompletelyBelow(withId(R.id.editTextSellValue)))
        onView(withId(R.id.editTextName)).check(isCompletelyAbove(withId(R.id.spinnerRarity)))
        onView(withId(R.id.buttonCreateDuck)).check(isCompletelyBelow(withId(R.id.spinnerRarity)))
    }

    @Test
    fun test2_2() {
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

        // Tot aquest percal per poder accedir al fragment de detalls...

        onView(withId(R.id.textViewSeed)).check(isCompletelyAbove(withId(R.id.textViewUrl)))
        onView(withId(R.id.textViewUrl)).check(isCompletelyAbove(withId(R.id.imageViewDuck)))
        onView(withId(R.id.textViewUrl)).check(isCompletelyAbove(withId(R.id.imageViewDuck)))
        onView(withId(R.id.imageViewDuck)).check(isCompletelyAbove(withId(R.id.editTextName)))
        onView(withId(R.id.editTextName)).check(isCompletelyAbove(withId(R.id.editTextSellValue)))
        onView(withId(R.id.editTextSellValue)).check(isCompletelyAbove(withId(R.id.spinnerRarity)))
        onView(withId(R.id.spinnerRarity)).check(isCompletelyAbove(withId(R.id.buttonUpdateDuck)))
        onView(withId(R.id.buttonUpdateDuck)).check(isCompletelyBelow(withId(R.id.spinnerRarity)))


        // Alguns checks adicionals per al mode landscape

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.imageViewDuck)).check(isCompletelyLeftOf(withId(R.id.editTextName)))
        onView(withId(R.id.textViewUrl)).check(isCompletelyBelow(withId(R.id.spinnerRarity)))
    }
}