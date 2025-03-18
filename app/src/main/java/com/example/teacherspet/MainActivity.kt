package com.example.teacherspet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.transition.Visibility
import com.example.teacherspet.model.FirebaseModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
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
        bottomNavBar?.visibility = View.GONE

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser?.uid !== null){
            bottomNavBar?.visibility = View.VISIBLE
        }



        bottomNavBar?.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.logout){
                logout()
            }

            false
        }

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        Log.d("LOGOUT", "Logged out")
    }
}