package com.example.teacherspet

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private var bottomNavBar: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainNavHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = mainNavHost.navController

        bottomNavBar = findViewById(R.id.bottom_navigation)
        hideBottomNavBar()


        bottomNavBar?.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.logout){
                logout()
            } else {
                NavigationUI.onNavDestinationSelected(item, navController!!)
                bottomNavBar!!.menu.findItem(item.itemId).isChecked = true
            }
            false
        }

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        Log.d("LOGOUT", "Logged out")

        navController?.navigate(R.id.action_aiHelperFragment_to_landingPageFragment)
        hideBottomNavBar()
    }

    fun showBottomNavBar(){
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser?.uid !== null){
            bottomNavBar?.visibility = View.VISIBLE
        }
    }

    fun hideBottomNavBar(){
        bottomNavBar?.visibility = View.GONE
    }
}