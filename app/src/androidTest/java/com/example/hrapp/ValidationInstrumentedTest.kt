package com.example.hrapp

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.hrapp.ui.claims.ClaimsFragment
import com.example.hrapp.ui.jitsi.JitsiFragment
import com.example.hrapp.ui.leaves.LeavesFragment
import org.hamcrest.Matchers.not

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will test the key feature's buttons (Login,Apply Leaves, Apply Claims, Jitsi)
 * Test case pass if toast message appears.
 * Delay is set to allow toast message to clear before next test case.
 */
@RunWith(AndroidJUnit4::class)
class ValidationInstrumentedTest {

    @get:Rule
    var activityRule = activityScenarioRule<LoginActivity>()

    private var decorView: View? = null


    @Before
    fun loadDecorView() {
        activityRule.scenario.onActivity { activity: LoginActivity ->
            decorView = activity.window.decorView
        }
    }

    /**
     * Test login button with (123,123) for employeeID and password inputs
     * Test case pass if toast message appears for invalid credentials
     */
    @Test
    fun testLogin() {
        onView(withId(R.id.etEmployeeID)).perform(
            ViewActions.typeText("123"),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.etPassword)).perform(
            ViewActions.typeText("123"),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.btnLogin)).perform(
            ViewActions.click()
        )

        onView(withText("Invalid employee ID/ password. \n" +
                "Please try again!")).
        inRoot(RootMatchers.withDecorView(not(decorView)))
            .check(matches(isDisplayed()));
    }
    /**
     * Test claim button without entering any inputs
     * Test case pass if toast message appears
     * Delay is set to allow toast message to clear before next test case.
     */
    @Test
    fun testClaimFragment() {
        launchFragmentInContainer<ClaimsFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.btn_upload_image)).perform(ViewActions.click())
        Thread.sleep(2000);
        onView(withText("Please enter amount!"))
            .inRoot(RootMatchers.withDecorView(not(decorView)))
            .check(matches(isDisplayed()));

    }
    /**
     * Test Jitsi button without entering any inputs
     * Test case pass if toast message appears
     * Delay is set to allow toast message to clear before next test case.
     */
    @Test
    fun testJitsiFragment() {
        launchFragmentInContainer<JitsiFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.joinRoom)).perform(ViewActions.click())
        Thread.sleep(2000);
        onView(withText("Please enter Room ID!"))
            .inRoot(RootMatchers.withDecorView(not(decorView)))
            .check(matches(isDisplayed()));

    }
    /**
     * Test leave button without entering any inputs
     * Test case pass if toast message appears
     * Delay is set to allow toast message to clear before next test case.
     */
    @Test
    fun testLeaveFragment() {
        launchFragmentInContainer<LeavesFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.btnSubmitLeave)).perform(ViewActions.click())
        Thread.sleep(2000);
        onView(withText("Please enter date!"))
            .inRoot(RootMatchers.withDecorView(not(decorView)))
            .check(matches(isDisplayed()));
    }


}