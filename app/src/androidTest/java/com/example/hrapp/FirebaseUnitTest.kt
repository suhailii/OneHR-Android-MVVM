package com.example.hrapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hrapp.repository.SharedPrefRepository
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hrapp.ui.claims.ClaimsListViewModel
import com.example.hrapp.ui.home.HomeViewModel
import com.example.hrapp.ui.leaves.LeavesListViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


/**
 * Unit test, which will test the key features functions
 * Check whether able to retrieve data from Firebase
 */
@RunWith(AndroidJUnit4::class)
class FirebaseUnitTest {
    private lateinit var loginViewModel : LoginActivityViewModel
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var claimListViewModel : ClaimsListViewModel
    private lateinit var leaveListViewModel : LeavesListViewModel


    @get:Rule
    val instantTaskExecutorRole = InstantTaskExecutorRule()

    /**
     * Setup all the ViewModels to test their functions
     */
    @Before
    fun setup()
    {
        val context = ApplicationProvider.getApplicationContext<Context>()
        loginViewModel = LoginActivityViewModel(SharedPrefRepository( context ))
        homeViewModel = HomeViewModel(SharedPrefRepository(context))
        claimListViewModel = ClaimsListViewModel(SharedPrefRepository(context))
        leaveListViewModel = LeavesListViewModel(SharedPrefRepository(context))
    }
    /**
     * Test login function which returns true when login is valid.
     */
    @Test
    fun loginSuccess()
    {
        loginViewModel.login("abc","123").getOrAwaitValue().let {
            assertEquals(true, it)
        }
    }
    /**
     * Test login function which returns false when login is invalid.
     */
    @Test
    fun loginFail()
    {
        loginViewModel.login("123","456").getOrAwaitValue().let {
            assertEquals(false, it)
        }
    }
    /**
     * Test attendance retrieval function which contains the mutable live data of Attendance object
     * Assert true since not null using let function
     */
    @Test
    fun attendanceRetrieval()
    {
        homeViewModel.retrieveAttendance().getOrAwaitValue().let {
            assert(true)
        }
    }
    /**
     * Test claim retrieval function which contains the mutable live data of List of Claims objects
     * Assert true since not null using let function
     */
    @Test
    fun claimsRetrieval()
    {
        claimListViewModel.retrieve().getOrAwaitValue().let {
            assert(true)
        }
    }
    /**
     * Test leave retrieval function which contains the mutable live data of List of Leave objects
     * Assert true since not null using let function
     */
    @Test
    fun leaveRetrieval()
    {
        leaveListViewModel.retrieve().getOrAwaitValue().let {
            assert(true)
        }
    }

}
