package com.example.teacherspet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val signInButton: Button = findViewById(R.id.signInButton)
        val logInButton: Button = findViewById(R.id.logInButton)

        signInButton.setOnClickListener {
            toSignIn()
        }

        logInButton.setOnClickListener {
            toLogIn()
        }
    }

    private fun toSignIn(){
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private fun toLogIn(){
//        startActivity(Intent(this, LogInActivity::class.java))
    }
}